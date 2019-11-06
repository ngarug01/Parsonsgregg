package com.ten10.training.javaparsons.webapp.controllers;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercisesControllerTest {



    private final ExerciseInformation exerciseInformation = new ExerciseInformation() {
        @Override
        public int getIdentifier() {
            return 1;
        }

        @Override
        public String getTitle() {
            return "Test Exercise 1";
        }

        @Override
        public String getDescription() {
            return "Do the thing";
        }
    };

    @Mock
    Exercise exercise;
    private Iterable<Exercise> exercises;
    private ExercisesController exercisesController;


    @BeforeEach
    public void setup() {
        exercises = singletonList(exercise);
        exercisesController = new ExercisesController(exercises);
    }

    @Test
    public void testControllerList() {
        when(exercise.getInformation()).thenReturn(exerciseInformation);

        List<ExerciseController.ExerciseInformation> exerciseList = exercisesController.getExercises();

        assertNotNull(exerciseList);
    }
}
