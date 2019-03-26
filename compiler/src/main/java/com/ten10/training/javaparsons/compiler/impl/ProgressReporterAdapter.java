package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.Locale;
import java.util.Objects;

class ProgressReporterAdapter implements DiagnosticListener<JavaFileObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressReporterAdapter.class);

    private final ProgressReporter progressReporter;

    ProgressReporterAdapter(ProgressReporter progressReporter) {
        this.progressReporter = Objects.requireNonNull(progressReporter, "progressReporter");
    }

    /**
     * Report diagnoses the code and logs compiler errors to an array
     * @param diagnostic an imported tool for checking code and finding compiler/runner errors
     */

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        LOGGER.debug("Received diagnostic: {}", diagnostic);
        switch(diagnostic.getKind()) {
            case ERROR:
                progressReporter.reportCompilerError(diagnostic.getLineNumber(), diagnostic.getMessage(Locale.UK));
                break;
            default:
                progressReporter.reportCompilerInfo(diagnostic.getLineNumber(), diagnostic.getMessage(Locale.UK));
        }
    }
}

