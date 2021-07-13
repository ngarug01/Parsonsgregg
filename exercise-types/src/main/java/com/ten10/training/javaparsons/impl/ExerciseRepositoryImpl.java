package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.Solution;
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
import java.util.function.Consumer;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    public final List<Exercise> exercises = new ArrayList<>();
    private final SolutionRunner runner;
    private final SolutionCompiler compiler;

    public ExerciseRepositoryImpl(SolutionCompiler compiler, SolutionRunner runner) {
        this.compiler = compiler;
        this.runner = runner;

    }

//    /**
//     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
//     * @param compiler Prepares an user input to be run.
//     * @param runner
//     */

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

    public SolutionRunner getRunner() {
        return runner;
    }

    public SolutionCompiler getCompiler() {
        return compiler;
    }

    public void addExercise(Consumer<ExerciseBuilder> builderConsumer) {
        ExerciseBuilder builder = new CreateExercise(compiler, runner);
        builderConsumer.accept(builder);
        Exercise exercise = builder.build();
        exercises.add(exercise);

    }


}
