package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;

public class LoadedEntryPointImpl implements SolutionRunner.LoadedEntryPoint {
    private final Object instance;
    private final Method method;
    private final Object[] parameters;
    private long timeoutMillis = 500;

    public LoadedEntryPointImpl(Object instance,
                                Method method,
                                Object[] parameters) {
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
                return SolutionRunner.RunResult.fromReturnValue(future.get(timeoutMillis, TimeUnit.MILLISECONDS));
            } else {
                return SolutionRunner.RunResult.fromReturnValue(future.get());
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            progressReporter.reportError(Phase.RUNNER, 37, "timeout error");
            return SolutionRunner.RunResult.failure();

        } catch (InterruptedException e) {
            future.cancel(true);
            progressReporter.reportError(Phase.RUNNER, 42, "interrupted error");
            return SolutionRunner.RunResult.failure();

        } catch (ExecutionException executionException) {
            future.cancel(true);
            // Something went wrong in the code that we were executing
            // almost certainly an exception in that code.
            // That exception is wrapped in two layers:
            // this ExecutionException (because we were running it in the executor)
            // and an InvocationTargetException (because we were running it through reflection)
            // We unwrap it here, and return it to the caller

            Throwable cause = executionException.getCause();
            if (cause instanceof InvocationTargetException) {
                Throwable originalException = cause.getCause();
                if (originalException != null) {
                    return SolutionRunner.RunResult.fromException(originalException);
                }

            }
            progressReporter.reportError(Phase.RUNNER, 62, "execution error");
            return SolutionRunner.RunResult.failure();

        } finally {
            executor.shutdownNow();
        }
    }

    @Override
    public void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}
