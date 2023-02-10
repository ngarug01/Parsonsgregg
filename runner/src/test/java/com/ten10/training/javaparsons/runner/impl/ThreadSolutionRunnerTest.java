package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
import com.ten10.training.javaparsons.runner.SolutionRunner.LoadedEntryPoint;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ThreadSolutionRunnerTest {


    private static final AtomicBoolean exampleMethodCalled = new AtomicBoolean(false);

    private static final AtomicBoolean instanceMethodCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesArgsCalled = new AtomicBoolean(false);
    private static final AtomicBoolean takesNoArgsCalled = new AtomicBoolean(false);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    private static final EntryPointBuilder startEntryPointBuilder = new EntryPointBuilderImpl();

    @SuppressWarnings("unused")
    static class Example {
        public static void main(String[] args) {

        }
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

        public static void takesNoArgs() {
            takesNoArgsCalled.set(true);
        }

        public void instanceMethod() {
            instanceMethodCalled.set(true);
        }

        public void throwsException() throws Exception {
            throw new Exception();
        }
        public static void staticMethodDisallowed(){
            instanceMethodCalled.set(true);
        }
    }

    @Test
    void runShouldBeAbleToCallStaticMethodOnClass() throws InvocationTargetException, IllegalAccessException {
        // Arrange
        exampleMethodCalled.set(false);
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypes()
            .allowStaticMethod()
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

    @Test
    void passesWithExceptionThrown() throws InvocationTargetException, IllegalAccessException {
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
    @Tag("slow")
    void methodsShouldTimeOut() throws InvocationTargetException, IllegalAccessException {
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

    @Test
    void methodsShouldNotTimeOut()  {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
       EntryPoint entryPoint = new EntryPointBuilderImpl()
            .className(Example.class.getName())
            .methodName("main")
            .parameterTypes(String[].class)
            .parameters(new Object[]{new String[]{}})
            .build();

        LoadedEntryPoint loadedEntryPoint = runner
            .load(entryPoint, currentThread().getContextClassLoader(), progressReporter)
            .orElseThrow(AssertionError::new);
        // Act
        loadedEntryPoint.run(progressReporter);
        //Assert
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> loadedEntryPoint.run(progressReporter));
    }

    @Test
    void methodsShouldAcceptParameters() throws InvocationTargetException, IllegalAccessException {
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

    @Test
    void reportLoadErrorWhenClassNameIncorrect() throws InvocationTargetException, IllegalAccessException {
        // Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className("xample")
            .methodName("exampleMethod")
            .parameterTypes()
            .parameters();

        EntryPoint callInformation = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();

        // Act
        runner.load(callInformation, currentThread().getContextClassLoader(), progressReporter);

        //Assert
        verify(progressReporter).reportError(Phase.LOADER, "No such class xample");
    }

    @Test
    void methodShouldAcceptNoParameters() throws InvocationTargetException, IllegalAccessException {
        //Arrange
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .methodName("takesNoArgs")
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
        assertTrue(takesNoArgsCalled.get(), "run() should have completed successfully");
    }


    @Test
    void handleInstanceMethods() throws InvocationTargetException, IllegalAccessException {
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

    @Test
    void handleStaticMethodIsDisallowed() throws InvocationTargetException, IllegalAccessException {
        EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(Example.class.getName())
            .requireInstanceMethod()
            .methodName("staticMethodDisallowed")
            .parameterTypes()
            .parameters();

        EntryPoint callInformation = entryPointBuilder.build();
        SolutionRunner runner = new ThreadSolutionRunner();
         runner.load(callInformation, currentThread().getContextClassLoader(), progressReporter);

        verify(progressReporter).reportError(Phase.LOADER, "This method staticMethodDisallowed is not an instance method");

    }

}
