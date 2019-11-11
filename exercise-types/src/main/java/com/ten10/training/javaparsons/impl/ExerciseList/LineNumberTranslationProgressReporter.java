package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.ProgressReporter;


public class LineNumberTranslationProgressReporter extends AbstractProgressReporterDecorator {

    private final int prefixLines;

    public LineNumberTranslationProgressReporter(String prefixCode, ProgressReporter progressReporter ) {
        super(progressReporter);
        prefixLines = prefixCode.split("\\R").length;
    }

    private long translateLineNumber(long lineNumber) {
        return lineNumber - prefixLines;
    }

    @Override
    public void reportCompilerError(long lineNumber, String message) {
        super.reportCompilerError(translateLineNumber(lineNumber), message);
    }

    @Override
    public void reportCompilerInfo(long lineNumber, String message) {
        super.reportCompilerInfo(translateLineNumber(lineNumber), message);
    }
}
