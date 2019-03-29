package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.CompleteTheCodeExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

public class CompleteTheCodeExercise implements Exercise {

    private final String exerciseName;
    private SolutionCompiler compiler;
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private final String answer;
    private final int id;
    private final String precedingCode;
    private final String followingCode;

    public CompleteTheCodeExercise(SolutionCompiler compiler, String precedingCode, String followingCode, String answer, String exerciseName, int id) {
        this.compiler = compiler;
        this.answer = answer;
        this.exerciseName = exerciseName;
        this.id = id;
        this.precedingCode = precedingCode;
        this.followingCode = followingCode;
    }

    @Override
    public int getIdentifier() {
        return id;
    }

    @Override
    public String getTitle() {
        return "Exercise " + getIdentifier() + ": " + exerciseName;
    }

    @Override
    public String getDescription() {
        return "Complete the Java code so that when run it will produce a string which reads " + answer;
    }

    @Override
    public String getPrecedingCode() {
        return precedingCode;
    }

    @Override
    public String getFollowingCode() {
        return followingCode;
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {
        return new CompleteTheCodeExerciseSolution(compiler, runner, userInput, answer, precedingCode, followingCode, progressReporter);
    }

    @Override
    public void close() throws Exception {

    }
}
