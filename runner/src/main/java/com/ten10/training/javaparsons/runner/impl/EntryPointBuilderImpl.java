package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.LoadedEntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.RunResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;
//import com.ten10.training.javaparsons.runner.EntryPointBuilder;


public class EntryPointBuilderImpl implements EntryPointBuilder {
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;


    @Override
    public EntryPointBuilder parameterTypes(Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    @Override
    public EntryPointBuilder parameters(Object... parameterList) {
        this.parameters = parameterList;
        return this;
    }

    @Override
    public EntryPointBuilder className(String className) {
        this.className = className;
        return this;
    }

    @Override
    public EntryPointBuilder methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    public EntryPoint build() {
        return new EntryPointImpl(this);
    }


    private class EntryPointImpl implements EntryPoint {
        private String className;
        private String methodName;
        private Class<?>[] parameterTypes;
        private Object[] parameters;
        private ClassLoader classLoader;


        public String getEntryPointClass() {
            return className;
        }

        public String getEntryPointMethod() {
            return methodName;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public Object[] getParameters() {
            return parameters;
        }

        public ClassLoader getClassLoader() {
            return classLoader;
        }

        EntryPointImpl(EntryPointBuilderImpl builder) {
            className = builder.className;
            methodName = builder.methodName;
            parameterTypes = builder.parameterTypes;
            parameters = builder.parameters;

        }

        //throws ClassNotFoundException
        @Override
        public LoadedEntryPoint load(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return new LoadedEntryPointImpl();
//            return null;
        }
    }


    private class LoadedEntryPointImpl implements LoadedEntryPoint {
        private long timeoutMillis = 500;
        private ExecutorService executor;
        private Future<Object> future;
        private final RunResult FAILURE = new RunResult() {
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

        @Override
        public RunResult run(ClassLoader classLoader,SolutionRunner.EntryPoint solution, ProgressReporter progressReporter) {
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

            Class<?> klass = null;
            try {
                klass = classLoader.loadClass(entryPointClassName);
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

            executor = Executors.newSingleThreadExecutor();
            future = executor.submit(() -> method.invoke(finalInstance, parameters));
            try {
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


        @Override
        public void setTimeout(long count, TimeUnit timeUnit) {
            timeoutMillis = timeUnit.toMillis(count);
        }
    }
    static Object returnValue;
    public static Object getReturnValue() {
        return returnValue;

    }

    public static void setReturnValue(Object o) {
        returnValue = o;
    }
}



