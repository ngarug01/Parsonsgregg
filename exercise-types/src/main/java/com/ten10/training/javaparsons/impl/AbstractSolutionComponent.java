package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * Abstract baseclass for Solution components that delegate to other solution components
 *
 * @param <I> The input type
 * @param <O> The output type - the type passed down the chain.
 */
public abstract class AbstractSolutionComponent<I, O> implements SolutionComponent<I> {

    private final List<SolutionComponent<O>> successors;

    AbstractSolutionComponent(List<SolutionComponent<O>> successors) {
        this.successors = unmodifiableList(successors);
    }

    final boolean feedValueDown(O value, ProgressReporter progressReporter) {
        for (SolutionComponent<O> successor : successors) {
            if (!successor.evaluate(value, progressReporter)) {
                return false;
            }
        }
        return true;
    }

    ProgressReporter decorateProgressReporter(ProgressReporter progressReporter) {
        return progressReporter;
    }

    abstract Optional<O> generateOutput(I input, ProgressReporter progressReporter);

    @Override
    public final boolean evaluate(I input, ProgressReporter progressReporter) {
        Optional<O> output = generateOutput(input, progressReporter);
        if (!output.isPresent()) return false;
        progressReporter = decorateProgressReporter(progressReporter);
        return feedValueDown(output.get(), progressReporter);
    }
}
