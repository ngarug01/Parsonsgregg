package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.PrintOutExercise;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ExerciseRepositoryImplTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final ExerciseRepositoryImpl exerciseRepository = new ExerciseRepositoryImpl(compiler);

    @Test
    void exercise1IsAHelloWorldExercise() {
        assertThat(exerciseRepository.getExerciseByIdentifier(1), is(instanceOf(PrintOutExercise.class)));
    }
    @Test
    void getNumberOfElementsInExercisesArray(){
        int result = exerciseRepository.getExerciseArraySize();
        assertEquals(3 , result);
    }
}
