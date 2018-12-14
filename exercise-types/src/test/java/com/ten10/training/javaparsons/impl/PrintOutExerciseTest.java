package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PrintOutExerciseTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final PrintOutExercise printOutExercise = new PrintOutExercise(compiler, "Answer","MY NAME",1);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    @Test
    void helloWorldExerciseIdentifierIs1() {
        assertEquals(1, printOutExercise.getIdentifier());
    }

    @Test
    void getSolutionFromUserInputReturnsHelloWorldSolution() {
        assertThat(printOutExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(ExerciseSolution.class)));
    }
    @Test
    void getTitleOfExercise(){
        assertEquals(printOutExercise.getTitle(),"Exercise 1: MY NAME");
    }
    @Test
    void getDescription(){
        assertEquals(printOutExercise.getDescription(),"Write a Java code which when run will produce a string which reads \"MY NAME\"");
    }
}
