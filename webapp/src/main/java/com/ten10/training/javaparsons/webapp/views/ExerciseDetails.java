package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;

public class ExerciseDetails {
    private final String path;
    private final ExerciseInformation exerciseInformation;

    public ExerciseDetails(String path, ExerciseInformation exerciseInformation) {
        this.path = path;
        this.exerciseInformation = exerciseInformation;
    }

    public String getPath() {
        return path;
    }

    public ExerciseInformation getExerciseInformation() {
        return exerciseInformation;
    }
}
