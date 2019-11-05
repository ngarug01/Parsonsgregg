package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.*;

public class ThreadSolutionRunner implements SolutionRunner {

    private static final RunResult FAILURE = new RunResult() {
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
    private long timeoutMillis = 500;
    private ExecutorService executor;
    private Future<Object> future;


    @Override
    public RunResult run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter) {
        // Pull data out of the entry point object
        String entryPointClassName = solution.getEntryPointClass();
        String entryPointMethodName = solution.getEntryPointMethod();
        Class<?>[] parameterTypes = solution.getParameterTypes();
        Object[] parameters = solution.getParameters();
        // Validate data. TODO: It would be worth validating that the types match the parameters, but primitives!
        if (parameters.length != parameterTypes.length) {
            throw new IllegalArgumentException("Parameter types and parameters must be the same length");
        }
        for (int i = 0; i < parameters.length; i++) {
            String a = parameters[i].getClass().toString().toLowerCase();
            String b = parameterTypes[i].toString().toLowerCase();
            if (!a.contains(b)) {
                throw new IllegalArgumentException("The types must match the parameters");
            }
        }


        // Locate the method we are going to invoke
        Class<?> klass = null;
        try {
            klass = classLoader.loadClass(entryPointClassName);
        } catch (ClassNotFoundException e) {
            progressReporter.reportRunnerError("No such class " + entryPointClassName);
        }
        Method method;
        try {
            method = klass.getMethod(entryPointMethodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            progressReporter.reportRunnerError("No such method " + entryPointMethodName);
            return FAILURE;
        }

        Object instance = null;
        if (!Modifier.isStatic(method.getModifiers())) {
            if (!entryPointMethodName.equals("main")) {
                try {
                    instance = klass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    progressReporter.reportRunnerError("Class object cannot be instantiated because it is an interface or an abstract class");
                } catch (IllegalAccessException e) {
                    progressReporter.reportRunnerError("Cannot access method due to visibility qualifiers");
                } catch (InvocationTargetException e) {
                    progressReporter.reportRunnerError("Exception thrown by an invoked method or constructor");
                } catch (NoSuchMethodException e) {
                    progressReporter.reportRunnerError("No such method");
                }
            } else {
                progressReporter.reportRunnerError("main method should be static");
                return FAILURE;
            }
        }
        final Object finalInstance = instance;

        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> method.invoke(finalInstance, parameters));
        try {
            Object returnValue;
            if (timeoutMillis != 0) {
                returnValue = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                returnValue = future.get();

            }
            return new RunResult() {
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
                    return returnValue;

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

    private boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }

}


