package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.*;

public class LoadedEntryPointImpl implements SolutionRunner.LoadedEntryPoint {
    private final Object instance;
    private final Method method;
    private final Object[] parameters;
    private long timeoutMillis = 500;

    private final SolutionRunner.RunResult FAILURE = new SolutionRunner.RunResult() {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean hasReturnValue() {
            return false;
        }

        @Override
        public Object getReturnValue() {
            throw new IllegalStateException();
        }
    };

    public LoadedEntryPointImpl(Object instance,
                                 Method method,
                                 Object[] parameters){
        this.instance = instance;
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public SolutionRunner.RunResult run(ProgressReporter progressReporter) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(() -> method.invoke(instance, parameters));
        try {
            if (timeoutMillis != 0) {
                EntryPointBuilderImpl.returnValue = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                EntryPointBuilderImpl.returnValue = future.get();

            }
            return new SolutionRunner.RunResult() {
                @Override
                public boolean isSuccess() {
                    return true;
                }

                @Override
                public boolean hasReturnValue() {
                    return !method.getReturnType().equals(Void.TYPE);
                }

                @Override
                public Object getReturnValue() {
                    return EntryPointBuilderImpl.returnValue;

                }
            };
        } catch (TimeoutException e) {
            future.cancel(true);
            progressReporter.reportRunnerError("timeout error");
            return FAILURE;

        } catch (InterruptedException e) {
            future.cancel(true);
            progressReporter.reportRunnerError("interrupted error");
            return FAILURE;

        } catch (ExecutionException e) {
            future.cancel(true);
            progressReporter.reportRunnerError("execution error");
            return FAILURE;

        } finally {
            executor.shutdownNow();
        }
    }

    @Override
    public void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}
