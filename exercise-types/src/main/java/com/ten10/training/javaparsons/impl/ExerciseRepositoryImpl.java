package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final SolutionCompiler compiler;

    public ExerciseRepositoryImpl(SolutionCompiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        if (identifier == 1)
            return new HelloWorldExercise(compiler);
        else
            return null;
    }
}
