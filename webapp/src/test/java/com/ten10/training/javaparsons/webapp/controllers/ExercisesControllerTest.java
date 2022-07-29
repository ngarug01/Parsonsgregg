package com.ten10.training.javaparsons.webapp.controllers;


import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.webapp.views.ExerciseDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExercisesControllerTest {

    private ExerciseRepository exercises;
    private ExercisesController exercisesController;
    private Exercise exercise1;
    private Exercise exercise2;


    @BeforeEach
    public void setup() {
        exercises = mock(ExerciseRepository.class);
        exercisesController = new ExercisesController(exercises);
        exercise1 = mock(Exercise.class);
        exercise2 = mock(Exercise.class);
        when(exercises.getExercisesSize()).thenReturn(2);
        when(exercises.getExerciseByIdentifier(1)).thenReturn(exercise1);
        when(exercises.getExerciseByIdentifier(2)).thenReturn(exercise2);
        when(exercises.spliterator()).thenReturn(asList(exercise1, exercise2).spliterator());
    }

    @Test
    public void testControllerCreatesList() {
        List<ExerciseDetails> exerciseList = exercisesController.getExercises();
        assertNotNull(exerciseList);
    }

    @Test
    public void exercisesControllerCoversAllExercises() {
        List<ExerciseDetails> exerciseList = exercisesController.getExercises();
        assertEquals(exercises.getExercisesSize(), exerciseList.size());
    }
}
