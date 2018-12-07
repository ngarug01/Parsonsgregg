package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;

public class HelloWorldSolution implements Solution, SolutionCompiler.CompilableSolution {

    private final SolutionCompiler compiler;
    private final String userInput;
    private final ErrorCollector errorCollector;

    HelloWorldSolution(SolutionCompiler compiler, String userInput, ErrorCollector errorCollector) {

        this.compiler = compiler;
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
}
