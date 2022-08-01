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

    private LoadedEntryPointImpl(Object instance,
                                 Method method,
                                 Object[] parameters){
        this.instance = instance;
        this.method = method;
        this.parameters = parameters;
    }
    static Optional<SolutionRunner.LoadedEntryPoint> load(SolutionRunner.EntryPoint entryPoint,
                                                          ClassLoader classLoader,
                                                          ProgressReporter progressReporter){
        Class<?>[] parameterTypes = entryPoint.getParameterTypes();
        Object[] parameters = entryPoint.getParameters();
        final boolean validParameters = parametersAreValid(parameterTypes, parameters,progressReporter);
        if( validParameters ){
            Optional<Class<?>> klass = getClass(entryPoint,classLoader,progressReporter);
            if(klass.isPresent() ){
                String entryPointMethodName = entryPoint.getEntryPointMethod();
                Optional<Method> method = getMethod(entryPointMethodName, klass.get(), parameterTypes, progressReporter);
                if( method.isPresent() ){
                    ClassInstance instance = getClassInstance(entryPointMethodName, klass.get(), method.get(), progressReporter);
                    if(!instance.hasFailed()){
                        //TODO get rid of all these if statements, should just be able to map...--Hannah
                       return Optional.of(new LoadedEntryPointImpl(instance.getInstance(), method.get(), parameters));
                    }
                }
            }
        }
        return Optional.empty();
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

    private static ClassInstance getClassInstance(String entryPointMethodName,
                                                  Class<?> klass,
                                                  Method method,
                                                  ProgressReporter progressReporter) {
        ClassInstance instance ;
        if (!Modifier.isStatic(method.getModifiers())) {
            if (!entryPointMethodName.equals("main")) {
                try {
                    instance = ClassInstance.of( klass.getDeclaredConstructor().newInstance() );
                } catch (InstantiationException e) {
                    instance = ClassInstance.neededButNotAvailable();
                    //TODO this should be a load error now --Hannah
                    progressReporter.reportRunnerError("Class object cannot be instantiated because it is an interface or an abstract class");
                } catch (IllegalAccessException e) {
                    instance = ClassInstance.neededButNotAvailable();
                    //TODO this should be a load error now --Hannah
                    progressReporter.reportRunnerError("Cannot access method due to visibility qualifiers");
                } catch (InvocationTargetException e) {
                    instance = ClassInstance.neededButNotAvailable();
                    //TODO this should be a load error now --Hannah
                    progressReporter.reportRunnerError("Exception thrown by an invoked method or constructor");
                } catch (NoSuchMethodException e) {
                    instance = ClassInstance.neededButNotAvailable();
                    //TODO this should be a load error now --Hannah
                    progressReporter.reportRunnerError("No such method");
                }
            } else {
                instance = ClassInstance.neededButNotAvailable();
                //TODO this should be a load error now --Hannah
                progressReporter.reportRunnerError("main method should be static");
            }
        }
        else{
            instance = ClassInstance.notNeeded();
        }
        return instance;
    }

    private static Optional<Method> getMethod(String entryPointMethodName,
                                              Class<?> klass,
                                              Class<?>[] parameterTypes,
                                              ProgressReporter progressReporter) {
        try {
            return Optional.of( klass.getMethod(entryPointMethodName, parameterTypes));
        } catch (NoSuchMethodException e) {
            progressReporter.reportRunnerError("No such method " + entryPointMethodName);
            return Optional.empty();
        }
    }

    private static Optional<Class<?>> getClass(SolutionRunner.EntryPoint entryPoint,
        ClassLoader classLoader,
        ProgressReporter progressReporter) {
        String entryPointClassName = entryPoint.getEntryPointClass();
        try {
           return Optional.of( classLoader.loadClass(entryPointClassName) ) ;
        } catch (ClassNotFoundException e) {
            progressReporter.reportRunnerError("No such class " + entryPointClassName);
           return Optional.empty();
        }
    }

    private static boolean parametersAreValid(Class<?>[] parameterTypes,
                                              Object[] parameters,
                                              ProgressReporter reporter) {
        // Validate data. TODO: It would be worth validating that the types match the parameters, but primitives!
        if (parameters.length != parameterTypes.length) {
            reporter.reportLoadError("Parameter types and parameters must be the same length");
            return false ;
        }
        //TODO could use streams here. --Hannah
        for (int i = 0; i < parameters.length; i++) {
            String a = parameters[i].getClass().toString().toLowerCase();
            String b = parameterTypes[i].toString().toLowerCase();
            if (!a.contains(b)) {
                reporter.reportLoadError("The types must match the parameters");
                return false ;
            }
        }
        return true;
    }


    @Override
    public void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}
