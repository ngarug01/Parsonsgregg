package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

public class PrintOutExercise implements Exercise {
    private final String exerciseName;
    private SolutionCompiler compiler;
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private final String answer;
    private final int id;

    PrintOutExercise(SolutionCompiler compiler, String answer , String exerciseName ,int id) {
        this.compiler = compiler;
        this.answer =answer;
        this.exerciseName = exerciseName;
        this.id = id;
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
        return "Write a Java code which when run will produce a string which reads \""+exerciseName+"\"";
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {
        return new ExerciseSolution(compiler, runner, userInput, progressReporter);
    }

    @Override
    public void close() throws Exception {
    }
}
