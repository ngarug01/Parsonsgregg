package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class WholeClassExerciseTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final WholeClassExercise wholeClassExercise = new WholeClassExercise(compiler, "Answer","MY NAME",1, "Write a Java code which when run will produce a string which reads");
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    @Test
    void helloWorldExerciseIdentifierIs1() {
        assertEquals(1, wholeClassExercise.getIdentifier());
    }

    @Test
    void getSolutionFromUserInputReturnsHelloWorldSolution() {
        assertThat(wholeClassExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(PrintOutExerciseSolution.class)));
    }
    @Test
    void getTitleOfExercise(){
        assertEquals(wholeClassExercise.getTitle(),"Exercise 1: MY NAME");
    }
    @Test
    void getDescription(){
        assertEquals(wholeClassExercise.getDescription(),"Write a Java code which when run will produce a string which reads MY NAME");
    }
}
