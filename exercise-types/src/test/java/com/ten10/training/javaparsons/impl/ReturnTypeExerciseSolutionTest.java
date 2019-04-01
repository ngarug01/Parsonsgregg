//package com.ten10.training.javaparsons.impl;
//
//import com.ten10.training.javaparsons.ProgressReporter;
//import com.ten10.training.javaparsons.compiler.SolutionCompiler;
//import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
//import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
//import com.ten10.training.javaparsons.runner.SolutionRunner;
//import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
//import org.junit.jupiter.api.Test;
//
//import javax.tools.ToolProvider;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//class ReturnTypeExerciseSolutionTest {
//    private SolutionCompiler compiler = mock(SolutionCompiler.class);
//    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
//    private ThreadSolutionRunner mockRunner = mock(ThreadSolutionRunner.class);
//    private ProgressReporter progressReporter = mock(ProgressReporter.class);
//    private final ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner,"userInput string inputted into solution",12, progressReporter);
//    private SolutionCompiler.CompilableSolution compilableSolution = mock(SolutionCompiler.CompilableSolution.class);
//    private ClassLoader classLoader = mock(ClassLoader.class);
//    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);
//
//
//   @Test
//    void checkEvaluate() throws Exception {
//        returnTypeExerciseSolution.evaluate();
//        verify(compiler).compile(returnTypeExerciseSolution, progressReporter);
//    }
//
//    @Test
//    void earlyReturnWhenCompileFails() throws Exception {
//        when(compiler.compile(compilableSolution, progressReporter)).thenReturn(false);
//        returnTypeExerciseSolution.evaluate();
//        verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
//    }
//
//    @Test
//    void checkGetFullClassText() {
//        assertEquals("userInput string inputted into solution", returnTypeExerciseSolution.getFullClassText());
//    }
//
//    @Test
//    void checkGetClassName() {
//        assertEquals("Main", returnTypeExerciseSolution.getClassName());
//    }
//
//    @Test
//    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
//        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
//        returnTypeExerciseSolution.evaluate();
//        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
//    }
//
//    @Test
//    void evaluateFailsOnIncorrectAnswer() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class Main{\npublic Integer main(String[] args){return 10;}}";
//        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
//        assertFalse(returnTypeExerciseSolution.evaluate());
//    }
//
//    @Test
//    void evaluatePasses() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class Main{\npublic Integer main(String[] args){return 12;}}";
//        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
//        assertTrue(returnTypeExerciseSolution.evaluate());
//    }
//
//    @Test
//    void infiniteLoop() throws Exception {
//        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
//        String userInput = "public class Main{\npublic Integer main(String[] args){\nwhile(true){}}}";
//        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
//        assertFalse(returnTypeExerciseSolution.evaluate());
//    }
//}
//


