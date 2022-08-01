package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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


    private ExecutorService executor;
    private Future<Object> future;


    private static ClassInstance getClassInstance(String entryPointMethodName,
                                                  Class<?> klass,
                                                  Method method,
                                                  ProgressReporter progressReporter) {
        ClassInstance instance;
        if (!Modifier.isStatic(method.getModifiers())) {
            if (!entryPointMethodName.equals("main")) {
                try {
                    instance = ClassInstance.of(klass.getDeclaredConstructor().newInstance());
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
        } else {
            instance = ClassInstance.notNeeded();
        }
        return instance;
    }

    private static Optional<Method> getMethod(String entryPointMethodName,
                                              Class<?> klass,
                                              Class<?>[] parameterTypes,
                                              ProgressReporter progressReporter) {
        try {
            return Optional.of(klass.getMethod(entryPointMethodName, parameterTypes));
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
            return Optional.of(classLoader.loadClass(entryPointClassName));
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
            return false;
        }
        //TODO could use streams here. --Hannah
        for (int i = 0; i < parameters.length; i++) {
            String a = parameters[i].getClass().toString().toLowerCase();
            String b = parameterTypes[i].toString().toLowerCase();
            if (!a.contains(b)) {
                reporter.reportLoadError("The types must match the parameters");
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<LoadedEntryPoint> load(EntryPoint entryPoint, ClassLoader loader, ProgressReporter reporter) {
        Class<?>[] parameterTypes = entryPoint.getParameterTypes();
        Object[] parameters = entryPoint.getParameters();
        final boolean validParameters = parametersAreValid(parameterTypes, parameters, reporter);
        if (!validParameters) {
            return Optional.empty();
        }

        Optional<Class<?>> klass = getClass(entryPoint, loader, reporter);
        if (klass.isEmpty()) {
            return Optional.empty();
        }

        String entryPointMethodName = entryPoint.getEntryPointMethod();
        Optional<Method> method = getMethod(entryPointMethodName, klass.get(), parameterTypes, reporter);
        if (method.isEmpty()) {
            return Optional.empty();
        }

        ClassInstance instance = getClassInstance(entryPointMethodName, klass.get(), method.get(), reporter);
        if (instance.hasFailed()) {
            return Optional.empty();
        }
        return Optional.of(new LoadedEntryPointImpl(instance.getInstance(), method.get(), parameters));
    }
}


