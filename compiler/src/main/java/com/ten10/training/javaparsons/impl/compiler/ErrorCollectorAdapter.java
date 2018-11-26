package com.ten10.training.javaparsons.impl.compiler;

import com.ten10.training.javaparsons.ErrorCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.Objects;

class ErrorCollectorAdapter implements DiagnosticListener<JavaFileObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorCollectorAdapter.class);

    private final ErrorCollector errorCollector;

    ErrorCollectorAdapter(ErrorCollector errorCollector) {
        this.errorCollector = Objects.requireNonNull(errorCollector, "errorCollector");
    }

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        LOGGER.debug("Received diagnostic: {}", diagnostic);
    }
}
