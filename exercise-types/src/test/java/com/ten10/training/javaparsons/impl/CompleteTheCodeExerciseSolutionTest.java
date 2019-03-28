package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.CompleteTheCodeExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompleteTheCodeExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final CompleteTheCodeExerciseSolution completeTheCodeExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, "userInput string inputted into solution", "Hello World!", "code", "code", progressReporter);


    @Test
    void checkEvaluate() throws Exception {
        completeTheCodeExerciseSolution.evaluate();
        verify(compiler).compile(completeTheCodeExerciseSolution, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("code" + "userInput string inputted into solution" + "code", completeTheCodeExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", completeTheCodeExerciseSolution.getClassName());
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        CompleteTheCodeExerciseSolution printOutExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, userInput, "Pie", "", "", progressReporter);
        printOutExerciseSolution.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    @Test
    void evaluateFailsOnIncorrectAnswer() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "System.out.println(\"Pie\");";
        CompleteTheCodeExerciseSolution completeTheCodeExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, userInput, "Potato", "public class Main { public static void Main {", "}}", progressReporter);
        completeTheCodeExerciseSolution.evaluate();
        verify(progressReporter).setSuccessfulSolution(false);
    }

    @Test
    void evaluatePasses() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "System.out.println(\"Pie\");";
        CompleteTheCodeExerciseSolution printOutExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, userInput, "Pie", "public class Main { public static void main (String[] args) { ", " }}", progressReporter);
        assertTrue(printOutExerciseSolution.evaluate());

    }
}
