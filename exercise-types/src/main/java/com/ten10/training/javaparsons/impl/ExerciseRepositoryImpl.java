package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.ArrayList;
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

    public int getExercisesSize() {
        return exercises.size();
    }

    /**
     * @param identifier The unique identifier for an exercise
     * @return The exercise with a given id.
     */

    @Override
    public Exercise getExerciseByIdentifier(int identifier) {
        return exercises.get(identifier - 1);  // Identifiers are one-based, not zero-based
    }

    @Override
    public int getIdentifierFor(Exercise exercise) {
        return exercises.indexOf(exercise) + 1;  // Identifiers are one-based, not zero-based
    }

    @Override
    public Iterator<Exercise> iterator() {
        return exercises.iterator();
    }

    public void addExercise(Consumer<ExerciseBuilder> builderConsumer) {
        CreateExercise builder = new CreateExercise(compiler, runner);
        builderConsumer.accept(builder);
        Exercise exercise = builder
            .setId(exercises.size() + 1)  // Ids are one-based, not zero-based
            .build();
        exercises.add(exercise);

    }


}
