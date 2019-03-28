package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PrintOutExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner,"userInput string inputted into solution","Hello World!", progressReporter);



   @Test
    void checkEvaluate() throws Exception {
        printOutExerciseSolution.evaluate();
        verify(compiler).compile(printOutExerciseSolution, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", printOutExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", printOutExerciseSolution.getClassName());
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);

        try {
            printOutExerciseSolution.evaluate();
            verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void evaluateFailsOnIncorrectAnswer(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Potato", progressReporter);

        try {
            printOutExerciseSolution.evaluate();
            verify(progressReporter).setSuccessfulSolution(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void evaluatePasses(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class Main{\npublic static void main(String[] args){\nSystem.out.println(\"Pie\");}}";
        PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, runner, userInput, "Pie", progressReporter);

        try {
            assertTrue(printOutExerciseSolution.evaluate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



