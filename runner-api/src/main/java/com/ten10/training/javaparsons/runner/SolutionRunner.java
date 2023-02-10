package com.ten10.training.javaparsons.runner;

import com.ten10.training.javaparsons.ProgressReporter;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public interface SolutionRunner {
    //An extra class with EntryPointBuilderImplementation
    interface EntryPointBuilder {
        EntryPointBuilder className(String className);

        EntryPointBuilder methodName(String methodName);

        EntryPointBuilder parameterTypes(Class<?>... parameterTypes);

        EntryPointBuilder parameters(Object... parameterList);

        EntryPointBuilder requireInstanceMethod();

        EntryPointBuilder allowStaticMethod();

        EntryPoint build();
    }

    interface EntryPoint {

        ClassLoader getClassLoader();

        String getEntryPointClass();

        String getEntryPointMethod();

        Class<?>[] getParameterTypes();

        Object[] getParameters();

        boolean staticMethodsDisallowed();

    }

    interface LoadedEntryPoint {
        RunResult run(ProgressReporter progressReporter);

         void setTimeout(long count, TimeUnit timeUnit);
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
        boolean ranToCompletion();

        boolean hasReturnValue();

        Object getReturnValue();

        Throwable getException();

        boolean hasException();

        static RunResult fromReturnValue(Object returnValue){
            return new RunResult() {
                @Override
                public boolean ranToCompletion() {
                    return true;
                }

                @Override
                public boolean hasReturnValue() {
                    return true;
                }

                @Override
                public Object getReturnValue() {
                    return returnValue;
                }

                @Override
                public Throwable getException() {
                    return null;
                }

                @Override
                public boolean hasException() {
                    return false;
                }
            };
        }

        static RunResult fromException(Throwable originalException){
            return new RunResult() {
                @Override
                public boolean ranToCompletion() {
                    return true;
                }

                @Override
                public boolean hasReturnValue() {
                    return false;
                }

                @Override
                public Object getReturnValue() {
                    throw new IllegalStateException();
                }

                @Override
                public Throwable getException() {
                    return originalException;
                }

                @Override
                public boolean hasException() {
                    return true;
                }
            };
        }

        static RunResult failure(){
            return new RunResult() {
                @Override
                public boolean ranToCompletion() {
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

                @Override
                public Throwable getException() {
                    throw new IllegalStateException();
                }

                @Override
                public boolean hasException() {
                    return false;
                }
            };
        }
    }

    /**
     * Takes a compiled solution and runs this from an expected {@link EntryPoint}. Fails if the user solution does not have
     * matching {@link EntryPoint} to what is expected.
     * The {@link EntryPoint} is required so the runner knows where to look to begin execution.
     * All Runtime exceptions and information is stored in the {@link ProgressReporter}.
     *
     * @param entryPoint         An {@link EntryPoint}, the name of the class and method(with its params) from where to run the code.
     * @param loader      Used to load the class from the expected {@link EntryPoint}.
     * @param reporter Stores any runtime exceptions.
     * @return The result of running the given compiled code.
     * @throws ReflectiveOperationException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    //EntryPointBuilder entryPoint();

    Optional<LoadedEntryPoint> load(EntryPoint entryPoint, ClassLoader loader, ProgressReporter reporter) throws InvocationTargetException, IllegalAccessException;
}

