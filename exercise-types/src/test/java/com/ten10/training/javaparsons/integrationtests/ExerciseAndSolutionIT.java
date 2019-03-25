package com.ten10.training.javaparsons.integrationtests;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;

class ExerciseAndSolutionIT {
private  final ProgressReporter progressReporter = mock(ProgressReporter.class);
    private static final String SUCCESSFUL_BUILD =
        "public class Main {" +
            " public static void main(String[] args) {" +
            "  System.out.println(\"Hello World!\");" +
            " }" +
            "}";

    private static final String UNSUCCESSFUL_BUILD =
        "public class Main {" +
            " public static void main(String[] args) {" +
            "  System.out.println(\"Hello World!\");" +
            " }";

    @Test
    void helloWorldCompilerBuild() throws Exception {
        final SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(SUCCESSFUL_BUILD, progressReporter);
        assertTrue(solution.evaluate());
    }

    @Test
    void helloWorldCompilerFailBuild() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD, progressReporter);
        assertFalse(solution.evaluate());
    }

    @Test
    void helloWorldCompilerLogsCompilationError() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD, progressReporter);
        solution.evaluate();
        Mockito.verify(progressReporter, atLeastOnce()).reportCompilerError(anyLong(), anyString());
    }
}
