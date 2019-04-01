package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReturnTypeExerciseSolutionIT {

    private SolutionRunner runner = new ThreadSolutionRunner();
    private SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
    private ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {

        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
        returnTypeExerciseSolution.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    @Test
    void evaluateFailsOnIncorrectAnswer() throws Exception {
        String userInput = "public class Main{\npublic Integer main(String[] args){return 10;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
        assertFalse(returnTypeExerciseSolution.evaluate());
    }

    @Test
    void evaluatePasses() throws Exception {
        String userInput = "public class Main{\npublic Integer main(String[] args){return 12;}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
        assertTrue(returnTypeExerciseSolution.evaluate());
    }

    @Test
    void infiniteLoop() throws Exception {
        String userInput = "public class Main{\npublic Integer main(String[] args){\nwhile(true){}}}";
        ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, runner, userInput, 12, progressReporter);
        assertFalse(returnTypeExerciseSolution.evaluate());
    }
}
