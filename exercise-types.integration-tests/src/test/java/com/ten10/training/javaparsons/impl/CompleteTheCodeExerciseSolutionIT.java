package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.CompleteTheCodeExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompleteTheCodeExerciseSolutionIT {

    private SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
    private SolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {

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
        assertFalse(completeTheCodeExerciseSolution.evaluate());
    }

    @Test
    void evaluatePasses() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "System.out.println(\"Pie\");";
        CompleteTheCodeExerciseSolution printOutExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, userInput, "Pie", "public class Main { public static void main (String[] args) { ", " }}", progressReporter);
        assertTrue(printOutExerciseSolution.evaluate());

    }

    @Test
    void compilerErrorTranslation() throws Exception {
        // Arrange - Get hold of the Progress Reporter that gets passed to the compiler
        ProgressReporter outsideProgressReporter = mock(ProgressReporter.class);
        String codePrefix = "line1\nline2\nline3\n";
        ProgressReporter insideProgressReporter = getLineNumberTranslatingProgressReporter(outsideProgressReporter, codePrefix);
        // Act
        insideProgressReporter.reportCompilerError(4, "It went wrong!");
        // Assert
        verify(outsideProgressReporter).reportCompilerError(1, "It went wrong!");
    }

    private ProgressReporter getLineNumberTranslatingProgressReporter(ProgressReporter outsideProgressReporter, String codePrefix) throws Exception {
        SolutionCompiler solutionCompiler = mock(SolutionCompiler.class);
        ArgumentCaptor<ProgressReporter> insideProgressReporterCaptor = ArgumentCaptor.forClass(ProgressReporter.class);
        CompleteTheCodeExerciseSolution completeTheCodeExerciseSolution = new CompleteTheCodeExerciseSolution(
            solutionCompiler, null, "", "", codePrefix, "Main", outsideProgressReporter);
        completeTheCodeExerciseSolution.evaluate();
        verify(solutionCompiler).compile(any(SolutionCompiler.CompilableSolution.class), insideProgressReporterCaptor.capture());
        return insideProgressReporterCaptor.getValue();
    }
    @Test
    void runTimeFailure() throws Exception {
        //ARRANGE
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput =
            "System.out.println(\"Hello World!\");\nwhile(true){}";
        CompleteTheCodeExerciseSolution completeTheCodeExerciseSolution =
            new CompleteTheCodeExerciseSolution(compiler, runner, userInput, "Hello World!",
                "public class Main { \npublic static void main (String[] args) {", "}\n}",
                progressReporter);

        //ACT
        boolean evaluateResult = completeTheCodeExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}
