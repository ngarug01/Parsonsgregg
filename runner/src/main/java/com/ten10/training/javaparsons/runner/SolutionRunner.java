package com.ten10.training.javaparsons.runner;

import com.ten10.training.javaparsons.ErrorCollector;

import java.util.concurrent.ExecutionException;

public interface SolutionRunner {
    interface EntryPoint {

        String getEntryPointClass();

        String getEntryPointMethod();

        Object[] getArguments();
    }

    boolean run(ClassLoader classLoader, EntryPoint solution, ErrorCollector errorCollector) throws ReflectiveOperationException, ExecutionException, InterruptedException;
}
