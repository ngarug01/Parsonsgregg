package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

public class HelloWorldExercise implements Exercise {
    private SolutionCompiler compiler;
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();

    HelloWorldExercise(SolutionCompiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public int getIdentifier() {
        return 1;
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ErrorCollector errorCollector) {
        return new HelloWorldSolution(compiler, runner, userInput, errorCollector);
    }

    @Override
    public void close() throws Exception {
    }
}
