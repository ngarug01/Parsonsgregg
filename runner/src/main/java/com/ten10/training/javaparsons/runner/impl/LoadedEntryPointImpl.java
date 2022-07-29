package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class LoadedEntryPointImpl implements SolutionRunner.LoadedEntryPoint {
    private final ClassLoader classLoader;
    private final SolutionRunner.EntryPoint entryPoint;
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

    public LoadedEntryPointImpl(ClassLoader classLoader, SolutionRunner.EntryPoint entryPoint) {
        this.classLoader = classLoader;
        this.entryPoint = entryPoint;
    }


    @Override
    public SolutionRunner.RunResult run(ProgressReporter progressReporter) {
        String entryPointClassName = entryPoint.getEntryPointClass();
        String entryPointMethodName = entryPoint.getEntryPointMethod();
        Class<?>[] parameterTypes = entryPoint.getParameterTypes();
        Object[] parameters = entryPoint.getParameters();
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

        Class<?> klass = null;
        try {
            klass = this.classLoader.loadClass(entryPointClassName);
        } catch (ClassNotFoundException e) {
            progressReporter.reportRunnerError("No such class " + entryPointClassName);
            return FAILURE;
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


        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(() -> method.invoke(finalInstance, parameters));
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
