package com.ten10.training.javaparsons.webapp.controllers;

import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.webapp.Application;
import com.ten10.training.javaparsons.webapp.views.ExerciseDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExercisesControllerTest {

    private ExerciseRepository exercises;
    private ExercisesController exercisesController;


    @BeforeEach
    public void setup() {
        exercises = new Application().exerciseRepository(mock(SolutionCompiler.class), mock(SolutionRunner.class));
        exercisesController = new ExercisesController(exercises);
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
