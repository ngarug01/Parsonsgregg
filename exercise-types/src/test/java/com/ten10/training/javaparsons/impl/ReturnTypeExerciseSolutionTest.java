package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReturnTypeExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner,"userInput string inputted into solution",12, progressReporter);



   @Test
    void checkEvaluate() throws Exception {
        returnTypeExerciseSolution.evaluate();
        verify(compiler).compile(returnTypeExerciseSolution, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", returnTypeExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", returnTypeExerciseSolution.getClassName());
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);

        try {
            returnTypeExerciseSolution.evaluate();
            verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void evaluateFailsOnIncorrectAnswer(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class Main{\npublic Integer main(String[] args){return 10;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);

        try {
            returnTypeExerciseSolution.evaluate();
            verify(progressReporter).setSuccessfulSolution(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void evaluatePasses(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class Main{\npublic Integer main(String[] args){return 12;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);

        try {
            assertTrue(returnTypeExerciseSolution.evaluate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



