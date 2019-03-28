package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.CompleteTheCodeExercise;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.CompleteTheCodeExerciseSolution;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.PrintOutExerciseSolution;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CompleteTheCodeExerciseTest {
    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final CompleteTheCodeExercise completeTheCodeExercise = new CompleteTheCodeExercise(compiler, "preceding code", "following code", "Answer","MY NAME",3);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    @Test
    void methodsStatementsHelloWorldExerciseIdentifierIs3() {
        assertEquals(3, completeTheCodeExercise.getIdentifier());
    }
    @Test
    void getSolutionFromUserInputReturnsHelloWorldSolution() {
        assertThat(completeTheCodeExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(CompleteTheCodeExerciseSolution.class)));
    }

    @Test
    void getTitleOfExercise(){
        assertEquals(completeTheCodeExercise.getTitle(),"Exercise 3: MY NAME");
    }
    @Test
    void getDescription(){
        assertEquals(completeTheCodeExercise.getDescription(),"Complete the Java code so that when run it will produce a string which reads Answer");
}
    @Test
    void getPrecedingCode(){
        assertEquals(completeTheCodeExercise.getPrecedingCode(), "preceding code");
    }
    @Test
    void getFollowingCode(){
        assertEquals(completeTheCodeExercise.getFollowingCode(), "following code");
    }

}
