package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class ThreadSolutionRunner implements SolutionRunner {

    private long timeoutMillis = 0;

    @Override
    public boolean run(ClassLoader classLoader, EntryPoint solution, ErrorCollector errorCollector) throws ReflectiveOperationException {
        String entryPointClassName = solution.getEntryPointClass();
        String entryPointMethodName = solution.getEntryPointMethod();

        Class<?> klass = classLoader.loadClass(entryPointClassName);
        Class<?>[] parameterTypes = new Class<?>[]{};
        Method method = klass.getMethod(entryPointMethodName, parameterTypes);
        method.invoke(null);
        return true;
    }

    void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}
