package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

import java.util.concurrent.ExecutionException;

public class HelloWorldSolution implements Solution, SolutionCompiler.CompilableSolution {

    private static SolutionRunner.EntryPoint entryPoint = new SolutionRunner.EntryPoint(){

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
            return new Class[]{String[].class};
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
    private ClassLoader loader;

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
    public boolean evaluate() {
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

    }

    public void run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        runner.run(loader,entryPoint,errorCollector);
    }
}
