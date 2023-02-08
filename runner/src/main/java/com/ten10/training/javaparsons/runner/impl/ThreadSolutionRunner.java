package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ThreadSolutionRunner implements SolutionRunner {

    private ExecutorService executor;
    private Future<Object> future;


    private static ClassInstance getClassInstance(String entryPointMethodName,
                                                  Class<?> klass,
                                                  Method method,
                                                  ProgressReporter progressReporter) {
        if (Modifier.isStatic(method.getModifiers())) {
            return ClassInstance.notNeeded();
        }

        if (entryPointMethodName.equals("main")) {
            //TODO this should be a load error now --Hannah
            progressReporter.reportError(Phase.RUNNER, "main method should be static");
            return ClassInstance.neededButNotAvailable();
        }

        try {
            return ClassInstance.of(klass.getDeclaredConstructor().newInstance());
        } catch (InstantiationException e) {
            //TODO this should be a load error now --Hannah
            progressReporter.reportError(Phase.RUNNER, "Class object cannot be instantiated because it is an interface or an abstract class");
            return ClassInstance.neededButNotAvailable();
        } catch (IllegalAccessException e) {
            //TODO this should be a load error now --Hannah
            progressReporter.reportError(Phase.RUNNER, "Cannot access method due to visibility qualifiers");
            return ClassInstance.neededButNotAvailable();
        } catch (InvocationTargetException e) {
            //TODO this should be a load error now --Hannah
            progressReporter.reportError(Phase.RUNNER, "Exception thrown by an invoked method or constructor");
            return ClassInstance.neededButNotAvailable();
        } catch (NoSuchMethodException e) {
            //TODO this should be a load error now --Hannah
            progressReporter.reportError(Phase.RUNNER, "No such method");
            return ClassInstance.neededButNotAvailable();
        }
    }

    private static Optional<Method> getMethod(String entryPointMethodName,
                                              Class<?> klass,
                                              Class<?>[] parameterTypes,
                                              ProgressReporter progressReporter) {
        try {
            return Optional.of(klass.getMethod(entryPointMethodName, parameterTypes));
        } catch (NoSuchMethodException e) {
            progressReporter.reportError(Phase.RUNNER, "No such method " + entryPointMethodName);
            return Optional.empty();
        }
    }

    private static Optional<Class<?>> getClass(SolutionRunner.EntryPoint entryPoint,
                                               ClassLoader classLoader,
                                               ProgressReporter progressReporter) {
        String entryPointClassName = entryPoint.getEntryPointClass();
        try {
            return Optional.of(classLoader.loadClass(entryPointClassName));
        } catch (NoClassDefFoundError e) {
            progressReporter.reportError(Phase.LOADER, "No such class " + entryPointClassName);
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            progressReporter.reportError(Phase.LOADER, "No such class " + entryPointClassName);
            return Optional.empty();
        }
    }

    private static boolean parametersAreValid(Class<?>[] parameterTypes,
                                              Object[] parameters,
                                              ProgressReporter reporter) {
        // Validate data. TODO: It would be worth validating that the types match the parameters, but primitives!
        if (parameters.length != parameterTypes.length) {
            reporter.reportError(Phase.LOADER, "Parameter types and parameters must be the same length");
            return false;
        }
        final boolean allParameterTypesMatch = IntStream.range(0, parameters.length)
            .allMatch(i -> {
                Class<?> a = parameters[i].getClass();
                Class<?> b = parameterTypes[i];
                String sa = a.toString().toLowerCase();
                String sb = b.toString().toLowerCase();
                //TODO should use something like isInstance or isAssignable here
                //return a.isInstance(b);
                //return b.isAssignableFrom(a);
                //did not work due to the differences between Integer and int
                return sa.contains(sb);
            });
        if (!allParameterTypesMatch) {
            reporter.reportError(Phase.LOADER, "The types must match the parameters");
            return false;
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
        if (!klass.isPresent()) {
            return Optional.empty();
        }

        String entryPointMethodName = entryPoint.getEntryPointMethod();
        Optional<Method> method = getMethod(entryPointMethodName, klass.get(), parameterTypes, reporter);
        if (!method.isPresent()) {
            return Optional.empty();
        }

        ClassInstance instance = getClassInstance(entryPointMethodName, klass.get(), method.get(), reporter);
        if (instance.hasFailed()) {
            return Optional.empty();
        }
        return Optional.of(new LoadedEntryPointImpl(instance.getInstance(), method.get(), parameters));
    }
}


