package com.ten10.training.javaparsons;

public interface ExerciseInformation extends AutoCloseable {
    /**
     *
     * @return the identifier identifying the exercise.
     */
    int getIdentifier();

    /**
     *
     * @return the stored title of this exercise.
     */
    String getTitle();

    /**
     *
     * @return the stored Description of this exercise.
     */
    String getDescription();

    String getPrecedingCode();

    String getFollowingCode();

    int getDropdownNumber();
}
