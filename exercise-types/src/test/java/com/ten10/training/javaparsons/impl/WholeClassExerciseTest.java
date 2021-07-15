package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

class WholeClassExerciseTest {
    private List<CapturedOutputChecker> capturedOutputCheckers = new ArrayList<>(Arrays.asList(new PrintOutChecker("Answer")));
    private List<ClassChecker> classCheckers = new ArrayList<>();
    private List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final Exercise wholeClassExercise = new WholeClassExercise(compiler, null, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, "MY NAME", 1, null, null);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);



//    @Test
//    void helloWorldExerciseIdentifierIs1() {
//        assertEquals(1, wholeClassExercise.getIdentifier());
//    }
//
//    @Test
//    void getSolutionFromUserInputReturnsBaseSolution() {
//        assertThat(wholeClassExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(BaseSolution.class)));
//    }
//    @Test
//    void getTitleOfExercise(){
//        assertEquals("Exercise 1: MY NAME",wholeClassExercise.getTitle());
//    }
//    @Test
//    void getDescription(){
//        assertEquals("Create a Java class that: \nThe program prints out Answer\n",wholeClassExercise.getDescription());
//    }

    @Test
    void getDescription() {
        ExerciseBuilder exerciseBuilder = new CreateExercise(null, null)
            .named("Whole Class \"Hello world\"")
            .checkOutputIs("Hello World!");
        Exercise exercise = ((CreateExercise) exerciseBuilder).build();
        String description = exercise.getInformation().getDescription();
        assertThat(description, startsWith("Create a Java class"));
    }

}
