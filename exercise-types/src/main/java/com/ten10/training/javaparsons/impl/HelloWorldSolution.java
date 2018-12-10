package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

import java.util.concurrent.ExecutionException;

public class HelloWorldSolution implements Solution, SolutionCompiler.CompilableSolution {

    private static SolutionRunner.EntryPoint entryPoint = new SolutionRunner.EntryPoint() {

        @Override
        public String getEntryPointClass() {
            return "Main";
        }

        @Override
        public String getEntryPointMethod() {
            return "main";
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return new Class<?>[]{String[].class};
        }

        @Override
        public Object[] getParameters() {
            return new Object[]{new String[]{}};
        }
    };
    private final SolutionCompiler compiler;
    private final ThreadSolutionRunner runner;
    private final String userInput;
    private final ErrorCollector errorCollector;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput();
    private byte[] byteCode;

    HelloWorldSolution(SolutionCompiler compiler, ThreadSolutionRunner runner, String userInput, ErrorCollector errorCollector) {

        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.errorCollector = errorCollector;
    }

    @Override
    public Exercise getExercise() {
        return new HelloWorldExercise(compiler);
    }

    @Override
    public boolean evaluate() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        captureConsoleOutput.start();
        try {
            return compile() && run();
        } finally {
            captureConsoleOutput.stop();
        }
    }

    private boolean compile() {
        return compiler.compile(this, errorCollector);
    }

    @Override
    public CharSequence getFullClassText() {
        return userInput;
    }

    @Override
    public String getClassName() {
        return "Main";
    }

    @Override
    public void recordCompiledClass(byte[] byteCode) {

        this.byteCode = byteCode;
    }

    private ClassLoader getClassLoader() {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        return new ClassLoader(contextClassLoader) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (name.equals(getClassName())) {
                    Class<?> clazz = defineClass(name, byteCode, 0, byteCode.length);
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
                return super.loadClass(name, resolve);
            }
        };
    }

    private boolean run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        return runner.run(getClassLoader(), entryPoint, errorCollector);
    }
}
