package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.StaticFieldExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

public class StaticFieldExercise implements Exercise {

    private final String exerciseName;
    private final Object answer;
    private final int id;
    private final String description;
    private SolutionCompiler compiler;
    private ThreadSolutionRunner runner = new ThreadSolutionRunner(); //TODO ThreadSolutionRunner dependency should be passed in

    //TODO this should eventually check for many fields and not just a single one.
    public StaticFieldExercise(SolutionCompiler compiler, Object answer, String exerciseName, int id, String description) {
        this.exerciseName = exerciseName;
        this.answer = answer;
        this.id = id;
        this.description = description;
        this.compiler = compiler;
    }

    @Override
    public int getIdentifier() {
        return id;
    }

    @Override
    public String getTitle() {
        return "Exercise "+getIdentifier()+": "+exerciseName;
    }

    @Override
    public String getDescription() {
        return description + " " + exerciseName;
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) throws Exception {
        return new StaticFieldExerciseSolution(compiler, runner, userInput, answer, progressReporter);
    }

    @Override
    @Deprecated
    public void close() {
        //TODO implement this
    }
}
