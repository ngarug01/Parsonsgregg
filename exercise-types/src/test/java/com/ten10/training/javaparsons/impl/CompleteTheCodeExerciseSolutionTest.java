package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.CompleteTheCodeExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompleteTheCodeExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private SolutionRunner runner = mock(SolutionRunner.class);
    private  ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final CompleteTheCodeExerciseSolution completeTheCodeExerciseSolution = new CompleteTheCodeExerciseSolution(compiler, runner, "userInput string inputted into solution", "Hello World!", "code", "code", progressReporter);


    @Test
    void checkEvaluate() throws Exception {
        completeTheCodeExerciseSolution.evaluate();
        verify(compiler).compile(isA(CompleteTheCodeExerciseSolution.class), isA(CompleteTheCodeExerciseSolution.LineNumberTranslationProgressReporter.class));
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("code\n" + "userInput string inputted into solution" + "code", completeTheCodeExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", completeTheCodeExerciseSolution.getClassName());
    }


}
