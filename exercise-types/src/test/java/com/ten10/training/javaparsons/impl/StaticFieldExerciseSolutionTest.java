package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.StaticFieldExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StaticFieldExerciseSolutionTest {

    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private SolutionRunner runner = mock(SolutionRunner.class);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private String userInput =
        "public class Main{\npublic static int i = 42;\npublic static void main(String[] args){}}";
    private final StaticFieldExerciseSolution staticFieldExerciseSolution =
        new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

    @Test
    void compilerIsCalled() throws Exception {
        //ACT
        staticFieldExerciseSolution.evaluate();
        //ASSERT
        verify(compiler).compile(staticFieldExerciseSolution, progressReporter);
    }

    @Test
    void getFullClassText() {
        assertEquals(userInput, staticFieldExerciseSolution.getFullClassText());
    }

    @Test
    void getClassName() {
        assertEquals("Main", staticFieldExerciseSolution.getClassName());
    }


}
