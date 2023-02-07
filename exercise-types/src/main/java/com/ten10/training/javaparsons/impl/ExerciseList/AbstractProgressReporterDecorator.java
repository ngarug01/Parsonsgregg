package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;

public abstract class AbstractProgressReporterDecorator implements ProgressReporter {

    private final ProgressReporter wrapped;

    protected AbstractProgressReporterDecorator(ProgressReporter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void storeCapturedOutput(String output) {
        this.wrapped.storeCapturedOutput(output);
    }

    @Override
    public void reportError(Phase phase, long linenumber, String message) {
        this.wrapped.reportError(phase, linenumber, message);
    }

    @Override
    public void reportError(Phase phase, String message) {
        this.wrapped.reportError(phase, message);
    }

    @Override
    public void reportInfo(Phase phase, String message) {
        this.wrapped.reportInfo(phase, message);
    }
}
