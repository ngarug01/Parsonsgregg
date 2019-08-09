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

    // TODO: Javadoc
    interface RunResult {
        boolean isSuccess();
        boolean hasReturnValue();
        Object getReturnValue();
    }

    /**
     * Takes a compiled solution and runs this from an expected {@link EntryPoint}. Fails if the user solution does not have
     * matching {@link EntryPoint} to what is expected.
     * The {@link EntryPoint} is required so the runner knows where to look to begin execution.
     * All Runtime exceptions and information is stored in the {@link ProgressReporter}.
     *
     * @param classLoader      Used to load the class from the expected {@link EntryPoint}.
     * @param solution         An {@link EntryPoint}, the name of the class and method(with its params) from where to run the code.
     * @param progressReporter Stores any runtime exceptions.
     * @return The result of running the given compiled code.
     * @throws ReflectiveOperationException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    RunResult run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter) throws ReflectiveOperationException, ExecutionException, InterruptedException;

}

