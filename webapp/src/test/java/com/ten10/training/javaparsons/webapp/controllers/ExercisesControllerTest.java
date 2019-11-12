package com.ten10.training.javaparsons.webapp.controllers;

import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.webapp.views.ExerciseDetails;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercisesControllerTest {

    @Mock
    private ExerciseRepository exercises;
    private ExercisesController exercisesController;


    @BeforeEach
    public void setup() {
        exercises = new ExerciseRepositoryImpl((solution, progressReporter) -> false, (classLoader, solution, progressReporter) -> null);
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
