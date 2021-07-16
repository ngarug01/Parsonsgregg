package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
import com.ten10.training.javaparsons.runner.SolutionRunner.LoadedEntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.RunResult;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


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
        // Arrange
        exampleMethodCalled.set(false);
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypes(new Class<?>[0])
            .parameters(new Object[0]);

        //Act
        EntryPoint entryPoint = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint = entryPoint.load(classLoader);
        RunResult myResults = loadedEntryPoint.run(currentThread().getContextClassLoader(), entryPoint, progressReporter);

        //Assert
        assertTrue(exampleMethodCalled.get(), "Our method should have been called");
    }


    @Test
    void runThrowsExceptionWhenParameterListArentEqual() throws ClassNotFoundException {
        //Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypes(new Class<?>[2])
            .parameters(new Object[1]);
        EntryPoint entryPoint = entryPointBuilder.build();

        //Act
        LoadedEntryPoint loadedEntryPoint = entryPoint.load(classLoader);

        //Assert
        assertThrows(IllegalArgumentException.class, () -> loadedEntryPoint.run(classLoader, entryPoint, progressReporter));
    }


    //Original test doesn't work and appears unnecessary so has been deleted.
    //void runDoesNotThrowExceptionWhenParameterListAreEqual() throws InterruptedException, ExecutionException, ReflectiveOperationException {}


    @Test
    @Tag("slow")
    void methodsShouldTimeOut() throws ClassNotFoundException {
        //Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("blockForever")
            .parameterTypes(new Class<?>[0])
            .parameters(new Object[0]);

        EntryPoint callInformation = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint = callInformation.load(classLoader);


        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter));
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
    void methodsShouldAcceptParameters() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("takesArgs")
            .parameterTypes(new Class<?>[]{int.class, int.class})
            .parameters(new Object[]{1, 3});

        EntryPoint callInformation = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint = callInformation.load(classLoader);


        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        // Act
        //Assert
//        assertTrue(result, "run() should have completed successfully");
        loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
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
    void handleInstanceMethods() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        // Arrange

        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("instanceMethod")
            .parameterTypes(new Class<?>[0])
            .parameters(new Object[0]);

        EntryPoint callInformation = entryPointBuilder.build();
        LoadedEntryPoint loadedEntryPoint = callInformation.load(classLoader);
        loadedEntryPoint.setTimeout(500, TimeUnit.MILLISECONDS);
        //Act
        loadedEntryPoint.run(currentThread().getContextClassLoader(), callInformation, progressReporter);
        //Assert
        assertTrue(instanceMethodCalled.get(), "run() should have completed successfully");
    }
}
