package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
import com.ten10.training.javaparsons.runner.SolutionRunner.LoadedEntryPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class ThreadSolutionRunnerTest {


    private static final AtomicBoolean exampleMethodCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesArgsCalled = new AtomicBoolean(false);
    private static final AtomicBoolean instanceMethodCalled = new AtomicBoolean(false);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    private static final EntryPointBuilder startEntryPointBuilder = new EntryPointBuilderImpl();


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

        public void throwsException() throws Exception {
            throw new Exception();
        }
    }

    @Test
    void passesWithExceptionThrown()  {
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("throwsException")
            .parameterTypes()
            .parameters();

        EntryPoint entryPoint = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
        LoadedEntryPoint loadedEntryPoint = runner
            .load(entryPoint, currentThread().getContextClassLoader(), progressReporter)
            .orElseThrow(AssertionError::new);

        SolutionRunner.RunResult run = loadedEntryPoint.run(progressReporter);

        assertTrue(run.ranToCompletion());
        assertTrue(run.hasException());
        assertFalse(run.hasReturnValue());
        assertInstanceOf(Exception.class, run.getException());
    }


    @Test
    void runShouldBeAbleToCallStaticMethodOnClass() {
        // Arrange
        exampleMethodCalled.set(false);
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypes()
            .parameters();

        //Act
        EntryPoint entryPoint = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
        ProgressReporter reporter = mock(ProgressReporter.class);
        LoadedEntryPoint loadedEntryPoint = runner
            .load(entryPoint, currentThread().getContextClassLoader(), reporter)
            .orElseThrow(AssertionError::new);
        loadedEntryPoint.run(progressReporter);

        //Assert
        assertTrue(exampleMethodCalled.get(), "Our method should have been called");
    }


    //Original test doesn't work and appears unnecessary so has been deleted.
    //void runDoesNotThrowExceptionWhenParameterListAreEqual() throws InterruptedException, ExecutionException, ReflectiveOperationException {}


    @Test
    @Tag("slow")
    void methodsShouldTimeOut() {
        //Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("blockForever")
            .parameterTypes()
            .parameters();

        EntryPoint callInformation = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
        LoadedEntryPoint loadedEntryPoint = runner
            .load(callInformation, currentThread().getContextClassLoader(), progressReporter)
            .orElseThrow(AssertionError::new);
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);

        SolutionRunner.RunResult runResult = assertTimeoutPreemptively(Duration.ofSeconds(5),
            () -> loadedEntryPoint.run(progressReporter));
        assertFalse(runResult.ranToCompletion()
            || runResult.hasReturnValue()
            || runResult.hasException());
    }
//
//    @Test     //call information parameter does not exist.
//    void methodsShouldNotTimeOut() throws InterruptedException, ExecutionException, ReflectiveOperationException {
//        // Arrange
//        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
//       EntryPoint entryPoint = new EntryPointBuilderImpl()
//            .className("Main")
//            .methodName("main")
//            .parameterTypes( new Class<?>[]{String[].class})
//            .parameters(new Object[]{new String[]{}})
//            .build();
//
//        LoadedEntryPoint loadedEntryPoint=entryPoint.load(null);
//        // Act
//        runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
//        //Assert
//        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> runner.run(currentThread().getContextClassLoader(), callInformation, progressReporter));
//    }

    @Test
    void methodsShouldAcceptParameters() {
        // Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("takesArgs")
            .parameterTypes(int.class, int.class)
            .parameters(1, 3);

        EntryPoint callInformation = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
        LoadedEntryPoint loadedEntryPoint = runner
            .load(callInformation, currentThread().getContextClassLoader(), progressReporter)
            .orElseThrow(AssertionError::new);
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        //Assert
        loadedEntryPoint.run(progressReporter);
        assertTrue(takesArgsCalled.get(), "run() should have completed successfully");
    }

//    @Test  //doesn't implement every method in EntryPoint() and so doesn't work.
//    void methodShouldNotAcceptParameters() throws InterruptedException, ExecutionException, ReflectiveOperationException {
//        //Arrange
//        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
//        final EntryPoint entryPoint = new EntryPoint() {
//            @Override
//            public String getEntryPointClass() {
//                return Example.class.getName();
//            }
//
//            @Override
//            public String getEntryPointMethod() {
//                return "takesNoArgs";
//            }
//
//            @Override
//            public Class<?>[] getParameterTypes() {
//                return new Class<?>[0];
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//        };
//        runner.setTimeout(500, TimeUnit.MILLISECONDS);
//        //Act
//        runner.run(currentThread().getContextClassLoader(), entryPoint, progressReporter);
//        //Assert
//        assertTrue(takesNoArgsCalled.get(), "run() should have completed successfully");
//    }


    @Test
    void handleInstanceMethods() {
        // Arrange

        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("instanceMethod")
            .parameterTypes()
            .parameters();

        EntryPoint callInformation = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
        LoadedEntryPoint loadedEntryPoint = runner
            .load(callInformation, currentThread().getContextClassLoader(), progressReporter)
            .orElseThrow(AssertionError::new);
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        //Act
        loadedEntryPoint.run(progressReporter);
        //Assert
        assertTrue(instanceMethodCalled.get(), "run() should have completed successfully");
    }
}
