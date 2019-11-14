package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import com.ten10.training.javaparsons.impl.ExerciseList.CreateExercise;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    public final List<Exercise> exercises;

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     * @param compiler Prepares an user input to be run.
     * @param runner
     */
    public ExerciseRepositoryImpl(SolutionCompiler compiler, SolutionRunner runner) {
        Exercise helloWorld = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Whole Class \"Hello world\"")
            .setId(1)
            .setPrefixCode(null)
            .setSuffixCode(null)
            .build();

        Exercise cruelWorld = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Goodbye Cruel World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Goodbye Cruel World!")
            .setId(2)
            .setPrefixCode(null)
            .setSuffixCode(null)
            .build();

        Exercise staticField = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>(Arrays.asList(new StaticFieldValueChecker("Has a static int field with a value of 3 \n", 3))))
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Static Field")
            .setId(3)
            .setPrefixCode(null)
            .setSuffixCode(null)
            .build();

        Exercise returnSquareNumber = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns an int with the value of 2 squared", 4))))
            .setName("Two Squared")
            .setId(4)
            .setPrefixCode(null)
            .setSuffixCode(null)
            .build();

        Exercise returnChar = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns a Char with value 'A'", 'A'))))
            .setName("Return Char A")
            .setId(5)
            .setPrefixCode(null)
            .setSuffixCode(null)
            .build();

        Exercise completeTheCodeHelloWorld = new CreateExercise()
            .setCompiler(compiler)
            .setRunner(runner)
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Complete the code - Hello World!")
            .setId(6)
            .setPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .setSuffixCode("}\n}")
            .build();



        exercises = Arrays.asList(helloWorld, cruelWorld, staticField, returnSquareNumber, returnChar, completeTheCodeHelloWorld);
    }

    public int getExercisesSize() {
        return exercises.size();
    }

    /**
     * @param identifier The unique identifier for an exercise
     * @return The exercise with a given id.
     */
    @Override
    public Exercise getExerciseByIdentifier(int identifier){
        return exercises.get(identifier);
    }

    @Override
    public int getIdentifierFor(Exercise exercise) {
        return exercises.indexOf(exercise);
    }

    @Override
    public Iterator<Exercise> iterator() {
        return exercises.iterator();
    }


}
