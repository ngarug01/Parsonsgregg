package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.PrintOutExercise;

import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final List<? extends Exercise> exercises;
    private SolutionCompiler compiler;

    public ExerciseRepositoryImpl(SolutionCompiler compiler) {
        PrintOutExercise helloWorld = new PrintOutExercise(compiler, "Hello World!", "Hello World!", 1);
        PrintOutExercise cruelWorld = new PrintOutExercise(compiler, "Goodbye Cruel World!", "Goodbye Cruel World!", 2);
        this.compiler = compiler;
        exercises = Arrays.asList(helloWorld, cruelWorld);
    }

    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        return exercises.get(identifier - 1);
    }

    @Override
    public int getExerciseArraySize() {
        return exercises.size();
    }
}
