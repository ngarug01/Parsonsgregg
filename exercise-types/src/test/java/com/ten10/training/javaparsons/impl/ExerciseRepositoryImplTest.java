package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.Collections.singletonList;
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
            .setCapturedOutputCheckers(singletonList(new PrintOutChecker("Hello World!")))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Whole Class \"Hello world\"")
            .setPrefixCode(null)
            .setSuffixCode(null));
        Exercise exercise = exerciseRepository.getExerciseByIdentifier(1);
        assertThat(exercise, is(instanceOf(WholeClassExercise.class)));
    }

}
