package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.Objects;

class ProgressReporterAdapter implements DiagnosticListener<JavaFileObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressReporterAdapter.class);

    private final ProgressReporter progressReporter;

    ProgressReporterAdapter(ProgressReporter progressReporter) {
        this.progressReporter = Objects.requireNonNull(progressReporter, "progressReporter");
    }

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
         LOGGER.debug("Received diagnostic: {}", diagnostic);
    }
}

