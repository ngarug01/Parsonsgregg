package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.*;

public class ThreadSolutionRunner implements SolutionRunner {

    private long timeoutMillis = 0;
    private ExecutorService executor;
    private Future<Object> future;

    @Override
    public Optional<Object> run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter) throws ReflectiveOperationException, ExecutionException, InterruptedException {
        // Pull data out of the entry point object
        String entryPointClassName = solution.getEntryPointClass();
        String entryPointMethodName = solution.getEntryPointMethod();
        Class<?>[] parameterTypes = solution.getParameterTypes();
        Object[] parameters = solution.getParameters();
        // Validate data. TODO: It would be worth validating that the types match the parameters, but primitives!
        if (parameters.length != parameterTypes.length) {
            throw new IllegalArgumentException("parameter types and parameters must be the same length");
        }

        // Locate the method we are going to invoke
        Class<?> klass = classLoader.loadClass(entryPointClassName);
        Method method;
        try {
            method = klass.getMethod(entryPointMethodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            progressReporter.reportRunnerError("No such method " + entryPointMethodName);
            return Optional.empty();
        }

        Object instance = null;
        if (!Modifier.isStatic(method.getModifiers())) {
            instance = klass.getDeclaredConstructor().newInstance();
        }
        final Object finalInstance = instance;

        return runMethod(parameters, method, finalInstance);
    }

    private Optional<Object> runMethod(Object[] parameters, Method method, Object finalInstance) throws InterruptedException, ExecutionException {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> method.invoke(finalInstance, parameters));
        try {
            if (timeoutMillis != 0) {
                future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                future.get();
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            return Optional.empty();
        } finally {
            executor.shutdownNow();
        }
        return Optional.ofNullable(method.getReturnType());
    }

    private boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }

    @Override
    public Object getMethodOutput() throws ExecutionException, InterruptedException {
        try {
            if (timeoutMillis != 0) {
                return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                return future.get();
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            return Optional.empty();
        } finally {
            executor.shutdownNow();
        }
    }

}


