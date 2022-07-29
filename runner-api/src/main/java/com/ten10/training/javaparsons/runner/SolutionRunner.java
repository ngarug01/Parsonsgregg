package com.ten10.training.javaparsons.runner;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public interface SolutionRunner {
    //An extra class with EntryPointBuilderImplementation
    interface EntryPointBuilder {
        EntryPointBuilder className(String className);

        EntryPointBuilder methodName(String methodName);

        EntryPointBuilder parameterTypes(Class<?>... parameterTypes);

        EntryPointBuilder parameters(Object... parameterList);

        EntryPoint build();
    }

    interface EntryPoint {

        ClassLoader getClassLoader();

        String getEntryPointClass();

        String getEntryPointMethod();

        Class<?>[] getParameterTypes();

        Object[] getParameters();

        LoadedEntryPoint load(ClassLoader classLoader) throws ClassNotFoundException;

    }

    interface LoadedEntryPoint {
        RunResult run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter);

        public void setTimeout(long count, TimeUnit timeUnit);
    }

    /**
     interface EntryPoint {

     String getEntryPointClass();

     String getEntryPointMethod();

     Class<?>[] getParameterTypes();

     Object[] getParameters();

     }
     **/

    /**
     * If the application runs with no time outs, and there is a return value,
     * the {@link RunResult} is stored in the form of an object.
     * If the application does not run, then no object of {@link RunResult }is returned.
     */


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
    RunResult run(ClassLoader classLoader, EntryPoint solution, ProgressReporter progressReporter);
    //EntryPointBuilder entryPoint();
}
