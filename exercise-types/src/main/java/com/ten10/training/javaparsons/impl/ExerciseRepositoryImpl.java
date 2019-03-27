package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.PrintOutExercise;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final List<? extends Exercise> exercises;

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     * @param compiler Prepares an user input to be run.
     * @param runner
     */
    public ExerciseRepositoryImpl(SolutionCompiler compiler, SolutionRunner runner) {
        PrintOutExercise helloWorld = new PrintOutExercise(compiler, runner, "Hello World!", "Hello World!", 1, "Write a Java code which when run will produce a string which reads");
        PrintOutExercise cruelWorld = new PrintOutExercise(compiler, runner, "Goodbye Cruel World!", "Goodbye Cruel World!", 2, "Write a Java code which when run will produce a string which reads");
        exercises = Arrays.asList(helloWorld, cruelWorld);

    }

    /**
     * @param identifier The unique identifier for an exercise
     * @return The exercise with a given id.
     */
    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        for(Exercise exercise : exercises){
            if(exercise.getIdentifier() == identifier){
                return exercise;
            }
        }
        return null;
    }

    /**
     * @return The number of exercises available.
     */
    @Override
    public int getExerciseArraySize() {
        return exercises.size();
    }
}
