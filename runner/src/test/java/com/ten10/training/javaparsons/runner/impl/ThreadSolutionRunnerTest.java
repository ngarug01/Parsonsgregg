package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSolutionRunnerTest {

    private static final AtomicBoolean methodCalled = new AtomicBoolean(false);

    @SuppressWarnings("unused")
    static class Example {
        public static void exampleMethod() {
            methodCalled.set(true);
        }

        @SuppressWarnings("InfiniteLoopStatement")
        public static void blockForever() {
            //noinspection StatementWithEmptyBody
            while (true) ;
        }
    }

    @Test
    void runShouldBeAbleToCallStaticMethodOnClass() throws ReflectiveOperationException, ExecutionException, InterruptedException {
        // Arrange
        methodCalled.set(false);
        ThreadSolutionRunner runner = new ThreadSolutionRunner();
        EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "exampleMethod";
            }
        };
        // Act
        boolean result = runner.run(Thread.currentThread().getContextClassLoader(), callInformation, new ErrorCollector() {
        });
        //Assert
        assertTrue(result, "run() should have completed successfully");
        assertTrue(methodCalled.get(), "Our method should have been called");
    }

    @Test
    @Tag("slow")

    void methodsShouldTimeOut() {
        // Arrange
        final ThreadSolutionRunner runner = new ThreadSolutionRunner();
        runner.setTimeout(500, TimeUnit.MILLISECONDS);
        final EntryPoint callInformation = new EntryPoint() {

            @Override
            public String getEntryPointClass() {
                return Example.class.getName();
            }

            @Override
            public String getEntryPointMethod() {
                return "blockForever";
            }
        };
        // Act
        boolean result = assertTimeoutPreemptively(Duration.ofSeconds(2),
            () -> runner.run(Thread.currentThread().getContextClassLoader(), callInformation, new ErrorCollector() {
        }));
        //Assert
        assertFalse(result, "run() should not have completed successfully");
    }
}

