package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.SolutionImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExerciseImplTest {
    private final List<CapturedOutputChecker> capturedOutputCheckers = singletonList(new PrintOutChecker("Answer"));
    private final List<ClassChecker> classCheckers = new ArrayList<>();
    private final List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final Exercise wholeClassExercise = new ExerciseImpl(compiler, null, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, "MY NAME", 1, null, null, null);
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void helloWorldExerciseIdentifierIs1() {
        assertEquals(1, wholeClassExercise.getInformation().getIdentifier());
    }

    @Test
    void getSolutionFromUserInputReturnsBaseSolution() {
        assertThat(wholeClassExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(SolutionImpl.class)));
    }

    @Test
    void getTitleOfExercise() {
        assertEquals("Exercise 1: MY NAME", wholeClassExercise.getInformation().getTitle());
    }

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
