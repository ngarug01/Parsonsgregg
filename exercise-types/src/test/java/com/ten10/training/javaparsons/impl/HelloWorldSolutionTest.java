package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HelloWorldSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ErrorCollector errorCollector = new ErrorCollector() {};
    private final HelloWorldSolution helloWorldSolution = new HelloWorldSolution(compiler, "userInput string inputted into solution", errorCollector);


    @Test
    void getExerciseReturnsHelloWorldExercise() {
        assertThat(helloWorldSolution.getExercise(), is(instanceOf(HelloWorldExercise.class)));
    }

    @Test
    void checkEvaluate() {
        helloWorldSolution.evaluate();
        verify(compiler).compile(helloWorldSolution, errorCollector);
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



