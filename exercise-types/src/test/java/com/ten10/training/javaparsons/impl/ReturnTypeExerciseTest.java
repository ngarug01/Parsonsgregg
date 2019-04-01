package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.ReturnTypeExerciseSolution;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ReturnTypeExerciseTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final ReturnTypeExercise returnTypeExercise = new ReturnTypeExercise(compiler, 12,"Twelve",3, "Write a Java method which when run will return");
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    @Test
    void exerciseIdentifierIs3() {
        assertEquals(3, returnTypeExercise.getIdentifier());
    }

    @Test
    void getSolutionFromUserInputReturnsReturnTypeSolution() {
        assertThat(returnTypeExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(ReturnTypeExerciseSolution.class)));
    }

    @Test
    void getTitleOfExercise(){
        assertEquals(returnTypeExercise.getTitle(),"Exercise 3: Twelve");
    }

    @Test
    void getDescription(){
        assertEquals(returnTypeExercise.getDescription(),"Write a Java method which when run will return Twelve");
    }
}
