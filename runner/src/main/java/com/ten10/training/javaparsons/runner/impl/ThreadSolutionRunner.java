package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.concurrent.*;

public class ThreadSolutionRunner implements SolutionRunner {

    private long timeoutMillis = 500;
    private ExecutorService executor;
    private Future<Object> future;

    /**
     * Takes a compiled solution and runs this from an expected {@link EntryPoint}. Fails if the user solution does not have
     * matching {@link EntryPoint} to what is expected.
     * The {@link EntryPoint} is required so the runner knows where to look to begin execution.
     * All Runtime exceptions and information is stored in the {@link ProgressReporter}.
     * @param classLoader       Used to load the class from the expected {@link EntryPoint}.
     * @param solution          An {@link EntryPoint}, the name of the class and method(with its params) from where to run the code.
     * @param progressReporter  Stores any runtime exceptions.
     * @return The result of running the given compiled code. {@link Optional#empty()} if the code fails to run.
     * @throws //ReflectiveOperationException
     * @throws //ExecutionException
     * @throws //InterruptedException
     */
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

        return runMethod(parameters, method, finalInstance, progressReporter);
    }

    private Optional<Object> runMethod(Object[] parameters, Method method, Object finalInstance, ProgressReporter progressReporter) throws InterruptedException, ExecutionException {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> method.invoke(finalInstance, parameters));
        try {
            if (timeoutMillis != 0) {
                return Optional.ofNullable(future.get(timeoutMillis, TimeUnit.MILLISECONDS));
            } else {
                return Optional.ofNullable(future.get());
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            progressReporter.reportRunnerError("timeout error");
            return Optional.empty();
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


