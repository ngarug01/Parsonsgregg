package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;


public class LineNumberTranslationProgressReporter extends AbstractProgressReporterDecorator {

    private final int prefixLines;

    public LineNumberTranslationProgressReporter(String prefixCode, ProgressReporter progressReporter) {
        super(progressReporter);
        prefixLines = prefixCode.split("\\R").length;
    }

    private long translateLineNumber(long lineNumber) {
        return lineNumber - prefixLines;
    }

    @Override
    public void reportError(Phase phase, long linenumber, String message) {
        super.reportError(phase, translateLineNumber(linenumber), message);
    }

    public void reportError(Phase phase, String message) {
        super.reportError(phase, message);
    }

    @Override
    public void reportInfo(Phase phase, String message) {
        super.reportInfo(phase, message);
    }
}
