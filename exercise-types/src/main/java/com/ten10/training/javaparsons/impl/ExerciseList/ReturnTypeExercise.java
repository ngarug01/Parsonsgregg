package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;

public class ReturnTypeExercise implements Exercise {
    private final String exerciseName;
    private SolutionCompiler compiler;
    private final Object answer;
    private final int id;
    private final String description;
    private final SolutionRunner runner;


    /**
     * Creates a new ReturnTypeExercise.
     * @param compiler Prepares an user input to be run.
     * @param answer Correct input to be compared with the user input.
     * @param exerciseName Description of the exercise.
     * @param id The unique identifier of an exercise.
     * @param description
     */
    public ReturnTypeExercise(SolutionCompiler compiler, SolutionRunner runner, Object answer, String exerciseName, int id, String description) {
        this.compiler = compiler;
        this.runner = runner;
        this.answer =answer;
        this.exerciseName = exerciseName;
        this.id = id;
        this.description = description;
    }



    /**
     * @return The unique identifier of an exercise.
     */
    @Override
    public int getIdentifier() {
        return id;
    }

    /**
     * @return A string containing the name of the exercise.
     */
    @Override
    public String getTitle() {
        return "Exercise "+getIdentifier()+": "+exerciseName;
    }

    /**
     * @return A string containing the description and instruction of an exercise.
     */
    @Override
    public String getDescription() {
        return  description+ " " + exerciseName;
    }

    /**
     * @param userInput The input provided by the user.
     * @param progressReporter The callback object to use when reporting compilation and test results.
     * @return A new ReturnTypeExerciseSolution from user input.
     */
    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {
        return new ReturnTypeExerciseSolution(compiler, runner, userInput, answer,  progressReporter);
    }

    @Override
    public void close() throws Exception {
    }
}
