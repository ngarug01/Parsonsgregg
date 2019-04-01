package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.FieldValueChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseList.CompleteTheCodeExercise;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final List<? extends Exercise> exercises;

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     * @param compiler Prepares an user input to be run.
     */
    public ExerciseRepositoryImpl(SolutionCompiler compiler) {
        WholeClassExercise helloWorld = new WholeClassExercise(compiler, new ArrayList<CapturedOutputChecker>(Arrays.asList(new PrintOutChecker("Hello World!"))), new ArrayList<ClassChecker>(), new ArrayList<MethodReturnValueChecker>(), "Whole Class \"Hello world\"", 1);
        WholeClassExercise cruelWorld = new WholeClassExercise(compiler, new ArrayList<CapturedOutputChecker>(Arrays.asList(new PrintOutChecker("Goodbye Cruel World!"))), new ArrayList<ClassChecker>(), new ArrayList<MethodReturnValueChecker>(), "Goodbye Cruel World!", 2);
        WholeClassExercise staticField = new WholeClassExercise(compiler, new ArrayList<CapturedOutputChecker>(), new ArrayList<ClassChecker>(Arrays.asList(new FieldValueChecker("Has a static int field with a value of 3 \n", int 3))), new ArrayList<MethodReturnValueChecker>(), "Static Field", 3);
        WholeClassExercise returnSquareNumber = new WholeClassExercise(compiler, new ArrayList<CapturedOutputChecker>(), new ArrayList<ClassChecker>(), new ArrayList<MethodReturnValueChecker>(), "Two Squared", 4);
        WholeClassExercise returnChar = new WholeClassExercise(compiler, new ArrayList<CapturedOutputChecker>(), new ArrayList<ClassChecker>(), new ArrayList<MethodReturnValueChecker>(), "Return Char A", 5);
        CompleteTheCodeExercise completeTheCodeHelloWorld = new CompleteTheCodeExercise(compiler, new ArrayList<CapturedOutputChecker>(), new ArrayList<ClassChecker>(), new ArrayList<MethodReturnValueChecker>(), "public class Main { \npublic static void main (String[] args) {", "}\n}", "Complete the code - Hello World!", 6);
        exercises = Arrays.asList(helloWorld, cruelWorld, staticField, returnSquareNumber, returnChar, completeTheCodeHelloWorld);

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
