package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExerciseRepositoryImplTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);

    @Test
    void exercise1IsAHelloWorldExercise() {
        ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler);
        assertThat(exerciseRepository.getExerciseByIdentifier(1), is(instanceOf(WholeClassExercise.class)));
    }
    @Test
    void getNumberOfElementsInExercisesArray(){
        ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler);
        int result = exerciseRepository.getExerciseArraySize();
        assertEquals(6 , result);
    }
}
