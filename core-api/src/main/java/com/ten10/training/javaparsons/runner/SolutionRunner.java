package com.ten10.training.javaparsons.runner;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface SolutionRunner {
    interface EntryPoint {

        String getEntryPointClass();

        String getEntryPointMethod();

        Class<?>[] getParameterTypes();

        Object[] getParameters();
    }

    Optional<Object> run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter) throws ReflectiveOperationException, ExecutionException, InterruptedException;
    Object getMethodOutput() throws ExecutionException, InterruptedException;
}

