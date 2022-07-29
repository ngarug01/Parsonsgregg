package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;

/**
 * Type definining part of a chain of solution components
 *
 * @param <I> The type of the input
 */
public interface SolutionComponent<I> {
    /**
     * Evaluate input returning true if it is successful.
     *
     * @param input
     * @param progressReporter
     * @return
     */
    boolean evaluate(I input, ProgressReporter progressReporter);
}
