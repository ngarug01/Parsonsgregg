package com.ten10.training.javaparsons.impl.exercise;

import com.ten10.training.javaparsons.Exercise;

class AbstractExercise implements Exercise {

    private final String className;

    AbstractExercise(String className) {
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void close() {
        // TODO
    }
}
