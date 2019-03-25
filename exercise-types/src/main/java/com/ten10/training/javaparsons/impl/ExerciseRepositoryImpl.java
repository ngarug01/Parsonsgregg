package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final List<? extends Exercise> exercises;

    public ExerciseRepositoryImpl(SolutionCompiler compiler, SolutionRunner runner) {
        exercises = Arrays.asList(new HelloWorldExercise(compiler, runner));
    }

    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        return exercises.get(identifier - 1);
    }
}
