package com.ten10.training.javaparsons;

public interface Solution {
    /**
     * @return the Exercise that this is a solution to.
     */
    Exercise getExercise();

    /**
     * Compile and run the solution.
     */
    boolean evaluate();
}
