package com.ten10.training.javaparsons.impl.compiler;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.SolutionCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Facade over the classes in javax.tools, particularly {@see javax.tools.JavaCompiler}</p>
 *
 * <p>Input files and class files are stored in memory</p>
 */
public class JavaSolutionCompiler implements SolutionCompiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSolutionCompiler.class);
    private final JavaCompiler compiler;


    JavaSolutionCompiler(JavaCompiler compiler) {
        this.compiler = Objects.requireNonNull(compiler, "compiler");
    }


    @Override
    public Optional<CompiledSolution> compile(final Solution solution, final ErrorCollector errorCollector) {

        Objects.requireNonNull(solution, "solution");
        Objects.requireNonNull(errorCollector, "errorCollector");

        final Exercise exercise = solution.getExercise();
        final CharSequence classText = solution.getFullClassText();

        LOGGER.info("Compiling {}", solution);

        DiagnosticListener<JavaFileObject> diagnostics = new ErrorCollectorAdapter(errorCollector);
        List<SimpleJavaFileObject> compilationUnits = Collections.singletonList(new SolutionJavaFile(solution));

        try (JavaFileManager fileManager = new InMemoryFileManager<>(compiler.getStandardFileManager(diagnostics, null, null))) {

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            if (!task.call()) {
                return Optional.empty();
            }

        } catch (IOException e) {
            LOGGER.error("Error closing fileManager", e);
            throw new RuntimeException(e);
        }

        return Optional.of(new CompiledSolution() {
            @Override
            public Exercise getExercise() {
                return exercise;
            }

            @Override
            public CharSequence getFullClassText() {
                return classText;
            }
        });
    }
}
