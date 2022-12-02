package com.ten10.training.javaparsons.runner.impl;

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

    private final SolutionRunner.RunResult FAILURE = new SolutionRunner.RunResult() {
        @Override
        public boolean ranToCompletion(){
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

        @Override
        public Throwable getException() {
            throw new IllegalStateException();
        }
    };

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
            Object returnValue;
            if (timeoutMillis != 0) {
                returnValue = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                returnValue = future.get();
            }
            return new SolutionRunner.RunResult() {
                @Override
                public boolean ranToCompletion() {
                    return true;

                }

                @Override
                public boolean hasReturnValue() {
                    return !method.getReturnType().equals(Void.TYPE);
                }

                @Override
                public Object getReturnValue() {
                    return returnValue;
                }

                @Override
                public Throwable getException() {
                    throw new IllegalStateException();
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
                    return new SolutionRunner.RunResult() {
                        @Override
                        public boolean ranToCompletion() {
                            return true;
                        }

                        @Override
                        public boolean hasReturnValue() {
                            return false;
                        }

                        @Override
                        public Object getReturnValue() {
                            throw new IllegalStateException();
                        }

                        @Override
                        public Throwable getException() {
                            return originalException;
                        }
                    };
                }

            }
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
