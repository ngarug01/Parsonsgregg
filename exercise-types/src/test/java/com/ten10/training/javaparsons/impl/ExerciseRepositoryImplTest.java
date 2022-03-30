package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import compiler.SolutionCompiler;
import runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

class ExerciseRepositoryImplTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final SolutionRunner runner = mock(SolutionRunner.class);

    @Test
    void exercise1IsAWholeClassExercise() {
        ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler, runner);
        exerciseRepository.addExercise(builder -> builder
            .named("Whole Class \"Hello world\"")
            .checkOutputIs("Hello World!"));
        Exercise exercise = exerciseRepository.getExerciseByIdentifier(1);
        assertThat(exercise, is(instanceOf(WholeClassExercise.class)));
    }

}
