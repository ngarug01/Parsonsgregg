package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PrintOutExerciseSolutionIT {
    private SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
    private SolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);


    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
        String userInput = "public class ain{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);

        printOutExerciseSolution.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    @Test
    void evaluateFailsOnIncorrectAnswer() throws Exception {
        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Potato", progressReporter);

        printOutExerciseSolution.evaluate();
    }

    @Test
    void evaluatePasses() throws Exception {
        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);

        assertTrue(printOutExerciseSolution.evaluate());
    }

    @Test
    void infiniteLoop() throws Exception {
        //ARRANGE
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput =
            "public class Main{" +
                " public static void main(String[] args){" +
                "  while(true){}" +
                " }" +
                "}";
        PrintOutExerciseSolution printOutExerciseSolution =
            new PrintOutExerciseSolution(compiler, runner, userInput, "Hello World!", progressReporter);

        //ACT
        boolean evaluateResult = printOutExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}



