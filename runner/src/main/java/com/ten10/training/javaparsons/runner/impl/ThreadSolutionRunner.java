package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ThreadSolutionRunner implements SolutionRunner {

    private long timeoutMillis = 0;

    @Override
    public boolean run(ClassLoader classLoader, EntryPoint solution, ErrorCollector errorCollector) throws ReflectiveOperationException, ExecutionException, InterruptedException {
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
        Method method = klass.getMethod(entryPointMethodName, parameterTypes);
        Object instance = null;
        if (!Modifier.isStatic(method.getModifiers())) {
            instance = klass.getConstructor().newInstance();
        }
        Object finalInstance = instance;

        // Invoke the method on an Executor
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(() -> method.invoke(finalInstance ,parameters));
        try {
            if (timeoutMillis != 0) {
                future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                future.get();
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            return false;
        } finally {
            executor.shutdownNow();
        }

        return true;
    }

    void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}
