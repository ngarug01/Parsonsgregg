package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;

import javax.tools.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>Facade over the classes in javax.tools, particularly {@link javax.tools.JavaCompiler}</p>
 *
 * <p>Input files and class files are stored in memory</p>
 */
public class JavaSolutionCompiler implements SolutionCompiler {

    //  private static final Logger LOGGER = LoggerFactory.getLogger(JavaSolutionCompiler.class);
    private final JavaCompiler compiler;


    public JavaSolutionCompiler(JavaCompiler compiler) {
        this.compiler = Objects.requireNonNull(compiler, "compiler");
    }


    @Override
    public boolean compile(final CompilableSolution solution, final ProgressReporter progressReporter) {
        Objects.requireNonNull(solution, "solution");
        Objects.requireNonNull(progressReporter, "progressReporter");

        // LOGGER.info("Compiling {}", solution);

        DiagnosticListener<JavaFileObject> diagnostics = new ProgressReporterAdapter(progressReporter);
        List<SimpleJavaFileObject> compilationUnits = Collections.singletonList(new SolutionJavaFile(solution));

        try (JavaFileManager fileManager = new InMemoryFileManager(compiler.getStandardFileManager(diagnostics, null, null), solution)) {
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();
        } catch (IOException e) {
            // LOGGER.error("Error closing fileManager", e);
            throw new RuntimeException(e);
        }
    }
}
