package com.ten10.training.javaparsons.runner.impl;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.LoadedEntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.RunResult;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;




class ThreadSolutionRunnerTest {


    private static final AtomicBoolean exampleMethodCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesArgsCalled = new AtomicBoolean(false);
    private static final AtomicBoolean instanceMethodCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesNoArgsCalled = new AtomicBoolean(false);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private static ClassLoader classLoader = mock(ClassLoader.class);
    private static EntryPointBuilder startEntryPointBuilder = new EntryPointBuilderImpl();



    @SuppressWarnings("unused")
    static class Example {
        public static void exampleMethod() {
            exampleMethodCalled.set(true);
        }

        @SuppressWarnings("InfiniteLoopStatement")
        public static void blockForever() {
            //noinspection StatementWithEmptyBody
            while (true) ;
        }

        public static void takesArgs(int a, int b) {
            takesArgsCalled.set(true);
        }

        public void instanceMethod() {
            instanceMethodCalled.set(true);
        }
    }


    @Test
    void runShouldBeAbleToCallStaticMethodOnClass() throws ReflectiveOperationException, ExecutionException, InterruptedException {
        /**
         EntryPoint callInformation = new EntryPoint() {

        @Override
        public String getEntryPointClass() {
        return Example.class.getName();
        }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }

        @Override
        public Class<?>[] getParameterTypes() {
        return new Class<?>[0];
        }

        @Override
        public Object[] getParameters() {
        return new Object[0];
        }
        };

         **/
        // Arrange

        exampleMethodCalled.set(false);
        EntryPointBuilder entryPointBuilder =startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypesList( new Class<?>[0])
            .getParameter(new Object[0]);

        //Act
        EntryPoint entryPoint = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint=entryPoint.load(classLoader);
        RunResult myResults=loadedEntryPoint.run(currentThread().getContextClassLoader(), entryPoint, progressReporter);

        //Assert
        assertTrue(exampleMethodCalled.get(), "Our method should have been called");
    }



    @Test
    void runThrowsExceptionWhenParameterListArentEqual() throws ClassNotFoundException {
        //Arrange
        EntryPointBuilder entryPointBuilder =startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypesList( new Class<?>[2])
            .getParameter(new Object[1]);

        EntryPoint entryPoint = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint=entryPoint.load(classLoader);

        /**
        EntryPoint entryPoint = new EntryPoint() {


            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[2];
            }

            @Override
            public Object[] getParameters() {
                return new Object[1];
            }
        };

        **/
        //Assert
        assertThrows(IllegalArgumentException.class, () -> loadedEntryPoint.run(classLoader, entryPoint, progressReporter));
    }

    @Test
    void runDoesNotThrowExceptionWhenParameterListAreEqual() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        //Arrange
        ThreadSolutionRunner threadSolutionRunner = new ThreadSolutionRunner();
        EntryPoint entryPoint = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "takesArgs";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[1];
            }

            @Override
            public Object[] getParameters() {
                return new Object[1];
            }
        };
        //Act
        threadSolutionRunner.run(currentThread().getContextClassLoader(), entryPoint, progressReporter);
        //Assert
        assertDoesNotThrow((ThrowingSupplier<IllegalAccessException>) IllegalAccessException::new);
    }

    @Test
    @Tag("slow")
    void methodsShouldTimeOut() throws ClassNotFoundException {
         //Arrange
            EntryPointBuilder entryPointBuilder =startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("blockForever")
            .parameterTypesList( new Class<?>[0])
            .getParameter(new Object[0]);

            EntryPoint callInformation = entryPointBuilder.build();
            LoadedEntryPoint loadedEntryPoint=callInformation.load(classLoader);

            /**
            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "blockForever";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        **/
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter));
    }

    @Test
    void methodsShouldNotTimeOut() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        // Act
        runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter));
    }

    @Test
    void methodsShouldAcceptParameters() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange
        EntryPointBuilder entryPointBuilder =startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("takesArgs")
            .parameterTypesList( new Class<?>[]{int.class, int.class})
            .getParameter(new Object[]{1,3});

        EntryPoint callInformation = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint=callInformation.load(classLoader);


        /**final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "takesArgs";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[]{int.class, int.class};
            }

            @Override
            public Object[] getParameters() {
                return new Object[]{1, 3};
            }
        };
         **/

        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        //Assert
//        assertTrue(result, "run() should have completed successfully");
        loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        assertTrue(takesArgsCalled.get(), "run() should have completed successfully");
    }

    @Test
    void methodShouldNotAcceptParameters() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        //Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        final EntryPoint entryPoint = new EntryPoint() {
            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "takesNoArgs";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        runner.setTimeout(500, TimeUnit.MILLISECONDS);
        //Act
        runner.run(currentThread().getContextClassLoader(), entryPoint, progressReporter);
        //Assert
        assertTrue(takesNoArgsCalled.get(), "run() should have completed successfully");
    }



    @Test
    void handleInstanceMethods() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange

        EntryPointBuilder entryPointBuilder =startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("instanceMethod")
            .parameterTypesList( new Class<?>[0])
            .getParameter(new Object[0]);

        EntryPoint callInformation = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint=callInformation.load(classLoader);

        /**
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "instanceMethod";
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return new Class<?>[0];
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
        **/
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
         //Act
        loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        assertTrue(instanceMethodCalled.get(), "run() should have completed successfully");
    }
}
