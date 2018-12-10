package com.ten10.training.javaparsons;

import java.util.concurrent.ExecutionException;

public interface Solution {
    /**
     * @return the Exercise that this is a solution to.
     */
    Exercise getExercise();

    /**
     * Compile and run the solution and return true if successfully compiled.
     */
    boolean evaluate() throws InterruptedException, ExecutionException, ReflectiveOperationException;
}
