package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.PrintOutExercise;
import com.ten10.training.javaparsons.impl.ExerciseList.ReturnTypeExercise;
import com.ten10.training.javaparsons.impl.ExerciseList.StaticFieldExercise;

import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final List<? extends Exercise> exercises;

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     *
     * @param compiler Prepares an user input to be run.
     */
    public ExerciseRepositoryImpl(SolutionCompiler compiler) {
        PrintOutExercise helloWorld = new PrintOutExercise(compiler, "Hello World!", "Hello World!", 1, "Write a Java code which when run will produce a string which reads");
        PrintOutExercise cruelWorld = new PrintOutExercise(compiler, "Goodbye Cruel World!", "Goodbye Cruel World!", 2, "Write a Java code which when run will produce a string which reads");
        StaticFieldExercise staticField = new StaticFieldExercise(compiler, 42, "42", 3, "Write a class which contains a static field with the value ");
        ReturnTypeExercise returnSquareNumber = new ReturnTypeExercise(compiler, 4, "Two Squared", 4, "Write a Java method which when run will return");
        ReturnTypeExercise returnChar = new ReturnTypeExercise(compiler, 'A', "Return Char A", 5, "Write a Java Method which will");
        exercises = Arrays.asList(helloWorld, cruelWorld, staticField, returnSquareNumber, returnChar);

    }

    /**
     * @param identifier The unique identifier for an exercise
     * @return The exercise with a given id.
     */
    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        for (Exercise exercise : exercises) {
            if (exercise.getIdentifier() == identifier) {
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
