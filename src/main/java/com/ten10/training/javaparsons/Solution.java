package com.ten10.training.javaparsons;

public interface Solution {
    /**
     * @return the Exercise that this is a solution to.
     */
    Exercise getExercise();

    /**
     * @return The full text of the submitted solution.
     */
    CharSequence getFullClassText();
}
