package com.ten10.training.javaparsons.integrationtests;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
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
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
    private final SolutionRunner runner = new ThreadSolutionRunner();

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

    private static final String SUCCESSFUL_BUILD_RETURN_TYPE=
        "public class Main {" +
            " public static Integer main(String[] args) {" +
            "  return 4;" +
            " }" +
            "}";
    private static final String UNSUCCESSFUL_BUILD_RETURN_TYPE =
        "public class Main {" +
            " public static Integer main(String[] args) {" +
            "  return 4;" +
            " }";

    private static final String SUCCESSFUL_BUILD_STATIC_FIELD=
        "public class Main {" +
            "public static int i=42;"+
            " public static void main(String[] args) {" +
            " } "+
            " }";
    private static final String UNSUCCESSFUL_BUILD_STATIC_FIELD=
        "public class Main {" +
            "public static int i=42;"+
            " public static void main(String[] args) {" +
            " } ";



    @Test
    void helloWorldCompilerBuild() throws Exception {
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(SUCCESSFUL_BUILD, progressReporter);
        assertTrue(solution.evaluate());
    }

    @Test
    void helloWorldCompilerFailBuild() throws Exception {
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD, progressReporter);
        assertFalse(solution.evaluate());


    }


    @Test
    void helloWorldCompilerLogsCompilationError() throws Exception {
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD, progressReporter);
        solution.evaluate();
        Mockito.verify(progressReporter, atLeastOnce()).reportCompilerError(anyLong(), anyString());
    }

    @Test
    void  returnTypeCompilerBuild() throws Exception {
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(4);
        Solution solution = exercise.getSolutionFromUserInput(SUCCESSFUL_BUILD_RETURN_TYPE, progressReporter);
        assertTrue(solution.evaluate());

    }

    @Test
    void returnTypeCompilerFailsBuild() throws Exception {
    final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
    Exercise exercise = repository.getExerciseByIdentifier(4);
    Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD_RETURN_TYPE, progressReporter);
    assertFalse(solution.evaluate());
    }

    @Test
    void returnTypeCompilerLogsCompilationError() throws Exception {
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(4);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD_RETURN_TYPE, progressReporter);
        solution.evaluate();
        Mockito.verify(progressReporter,atLeastOnce()).reportCompilerError(anyLong(),anyString());
    }

    @Test
    void  staticFieldCompilerBuild() throws Exception {
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(3);
        Solution solution = exercise.getSolutionFromUserInput(SUCCESSFUL_BUILD_STATIC_FIELD, progressReporter);
        assertTrue(solution.evaluate());

    }

    @Test
    void staticFieldCompilerFailsBuild() throws Exception {
        final ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(3);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD_STATIC_FIELD, progressReporter);
        assertFalse(solution.evaluate());
    }

    @Test
    void staticFieldCompilerLogsCompilationError() throws Exception {
        ExerciseRepository repository = new ExerciseRepositoryImpl(compiler, runner);
        Exercise exercise = repository.getExerciseByIdentifier(3);
        Solution solution = exercise.getSolutionFromUserInput(UNSUCCESSFUL_BUILD_STATIC_FIELD, progressReporter);
        solution.evaluate();
        Mockito.verify(progressReporter,atLeastOnce()).reportCompilerError(anyLong(),anyString());
    }
}
