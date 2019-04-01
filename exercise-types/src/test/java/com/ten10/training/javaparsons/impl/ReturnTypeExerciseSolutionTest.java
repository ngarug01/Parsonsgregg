package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReturnTypeExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private SolutionRunner mockRunner = mock(SolutionRunner.class);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final ReturnTypeExerciseSolution returnTypeExerciseSolution = new ReturnTypeExerciseSolution(compiler, mockRunner,"userInput string inputted into solution",12, progressReporter);
    private SolutionCompiler.CompilableSolution compilableSolution = mock(SolutionCompiler.CompilableSolution.class);
    private ClassLoader classLoader = mock(ClassLoader.class);
    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);


   @Test
    void checkEvaluate() throws Exception {
        returnTypeExerciseSolution.evaluate();
        verify(compiler).compile(returnTypeExerciseSolution, progressReporter);
    }

    @Test
    void earlyReturnWhenCompileFails() throws Exception {
        when(compiler.compile(compilableSolution, progressReporter)).thenReturn(false);
        returnTypeExerciseSolution.evaluate();
        Mockito.verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", returnTypeExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", returnTypeExerciseSolution.getClassName());
    }

}



