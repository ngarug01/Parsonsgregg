package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadSolutionRunner implements SolutionRunner {

    private static final RunResult FAILURE = new RunResult() {
        @Override
        public boolean isSuccess() {
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
    };


    private long timeoutMillis = 500;
    private ExecutorService executor;
    private Future<Object> future;

    @Override
    public LoadedEntryPoint load(ClassLoader loader, EntryPoint entryPoint, ProgressReporter reporter) {
        return new LoadedEntryPointImpl(loader, entryPoint);
    }

    private boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    void setTimeout(long count, TimeUnit timeUnit) {
        timeoutMillis = timeUnit.toMillis(count);
    }
}


