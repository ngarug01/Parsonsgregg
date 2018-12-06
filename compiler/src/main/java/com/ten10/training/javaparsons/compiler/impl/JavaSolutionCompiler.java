package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Facade over the classes in javax.tools, particularly {@see javax.tools.JavaCompiler}</p>
 *
 * <p>Input files and class files are stored in memory</p>
 */
class JavaSolutionCompiler implements SolutionCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSolutionCompiler.class);
    private final JavaCompiler compiler;


    JavaSolutionCompiler(JavaCompiler compiler) {
        this.compiler = Objects.requireNonNull(compiler, "compiler");
    }


    @Override
    public boolean compile(final CompilableSolution solution, final ErrorCollector errorCollector) {

        Objects.requireNonNull(solution, "solution");
        Objects.requireNonNull(errorCollector, "errorCollector");

        final CharSequence classText = solution.getFullClassText();

        LOGGER.info("Compiling {}", solution);

        DiagnosticListener<JavaFileObject> diagnostics = new ErrorCollectorAdapter(errorCollector);
        List<SimpleJavaFileObject> compilationUnits = Collections.singletonList(new SolutionJavaFile(solution));

        try (JavaFileManager fileManager = new InMemoryFileManager(compiler.getStandardFileManager(diagnostics, null, null))) {

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();

        } catch (IOException e) {
            LOGGER.error("Error closing fileManager", e);
            throw new RuntimeException(e);
        }

    }
}
