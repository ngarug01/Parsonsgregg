package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

class PrintOutExerciseSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private SolutionRunner mockRunner = mock(SolutionRunner.class);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final PrintOutExerciseSolution printOutExerciseSolution = new PrintOutExerciseSolution(compiler, mockRunner, "userInput string inputted into solution", "Hello World!", progressReporter);
    private ClassLoader classLoader = mock(ClassLoader.class);
    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);


   @Test
    void checkEvaluate() throws Exception {
        printOutExerciseSolution.evaluate();
        verify(compiler).compile(printOutExerciseSolution, progressReporter);
    }

    @Test
    void doesNotRunWhenCompileFails() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        when(compiler.compile(printOutExerciseSolution, progressReporter)).thenReturn(false);
        Mockito.verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
    }


    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", printOutExerciseSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", printOutExerciseSolution.getClassName());
    }

    //evaluating the output should be broken into another method then this test can work. (call never on that method).
    @Test
    void doesNotEvaluateResultWhenCompileFails() throws ExecutionException, InterruptedException {
       when(compiler.compile(printOutExerciseSolution, progressReporter)).thenReturn(false);
       //Mockito.verify(printOutExerciseSolution, never()).
    }

}



