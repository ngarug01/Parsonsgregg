package com.ten10.training.javaparsons;

public interface ExerciseInformation {

    Integer getIdentifier();

    /**
     * @return the stored title of this exercise.
     */
    String getTitle();

    /**
     * @return the stored Description of this exercise.
     */
    String getDescription();

    default String getPrecedingCode() {
        return null;
    }

    default String getFollowingCode() {
        return null;
    }

    default String getExerciseHint() {
        return null;
    }

}
