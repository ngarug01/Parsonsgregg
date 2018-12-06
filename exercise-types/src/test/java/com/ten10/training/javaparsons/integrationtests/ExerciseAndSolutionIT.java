package com.ten10.training.javaparsons.integrationtests;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExerciseAndSolutionIT {

    private static final String SUCCESSFUL_BUILD = "" +
        "public class Main {" +
        " public static void main(String[] args) {" +
        "  System.out.println(\"Hello World!\");" +
        " }" +
        "}";

    private static final String UNSUCCESSFUL_BUILD = "" +
        "public class Main {" +
        " public static void main(String[] args) {" +
        "  System.out.println(\"Hello World!\");" +
        " }" +
        "";

    @Test
    void helloWorldCompilerBuild() {
        final SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(SUCCESSFUL_BUILD, new ErrorCollector() {
        });
        assertTrue(solution.evaluate());
    }

    @Test
    void helloWorldCompilerFailBuild() {
        final SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD, new ErrorCollector() {
        });
        assertFalse(solution.evaluate());
    }
}
