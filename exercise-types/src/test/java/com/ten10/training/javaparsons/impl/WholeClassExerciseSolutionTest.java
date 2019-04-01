//package com.ten10.training.javaparsons.impl;
//
//import com.ten10.training.javaparsons.ProgressReporter;
//import com.ten10.training.javaparsons.compiler.SolutionCompiler;
//import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
//import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
//import com.ten10.training.javaparsons.runner.SolutionRunner;
//import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
//import org.junit.jupiter.api.Test;
//
//import javax.tools.ToolProvider;
//
//import java.util.Optional;
//import java.util.concurrent.ExecutionException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//class WholeClassExerciseSolutionTest {
//    private SolutionCompiler compiler = mock(SolutionCompiler.class);
//    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
//    private ThreadSolutionRunner mockRunner = mock(ThreadSolutionRunner.class);
//    private ProgressReporter progressReporter = mock(ProgressReporter.class);
//    private final PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, "userInput string inputted into solution", "Hello World!", progressReporter);
//    private ClassLoader classLoader = mock(ClassLoader.class);
//    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);
//
//
//   @Test
//    void checkEvaluate() throws Exception {
//        printOutExerciseSolution.evaluate();
//        verify(compiler).compile(printOutExerciseSolution, progressReporter);
//    }
//
//    @Test
//    void doesNotRunWhenCompileFails() throws InterruptedException, ExecutionException, ReflectiveOperationException {
//        when(compiler.compile(printOutExerciseSolution, progressReporter)).thenReturn(false);
//        verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
//    }
//
//
//    @Test
//    void checkGetFullClassText() {
//        assertEquals("userInput string inputted into solution", printOutExerciseSolution.getFullClassText());
//    }
//
//    @Test
//    void checkGetClassName() {
//        assertEquals("Main", printOutExerciseSolution.getClassName());
//    }
//
//    @Test
//    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class ain{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
//        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);
//        printOutExerciseSolution.evaluate();
//        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
//    }
//
//    @Test
//    void evaluateFailsOnIncorrectAnswer() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
//        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Potato", progressReporter);
//
//        assertFalse(printOutExerciseSolution.evaluate());
//
//    }
//
//    @Test
//    void evaluatePasses() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
//        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);
//        assertTrue(printOutExerciseSolution.evaluate());
//
//    }
//}
//
//

