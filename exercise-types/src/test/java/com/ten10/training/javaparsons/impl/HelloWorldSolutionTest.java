package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HelloWorldSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private SolutionRunner runner = mock(SolutionRunner.class);
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final HelloWorldSolution helloWorldSolution = new HelloWorldSolution(compiler, runner,"userInput string inputted into solution", progressReporter);


    @Test
    void getExerciseReturnsHelloWorldExercise() {
        assertThat(helloWorldSolution.getExercise(), is(instanceOf(HelloWorldExercise.class)));
    }

    @Test
    void checkEvaluate() throws Exception {
        helloWorldSolution.evaluate();
        verify(compiler).compile(helloWorldSolution, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", helloWorldSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", helloWorldSolution.getClassName());
    }

}



