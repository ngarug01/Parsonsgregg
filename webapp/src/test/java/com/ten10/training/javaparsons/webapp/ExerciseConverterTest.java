package com.ten10.training.javaparsons.webapp;

import com.ten10.training.javaparsons.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExerciseConverterTest {

    @Mock
    ExerciseRepository repository;

    @Test
    void convert() {
        ExerciseConverter converter = new ExerciseConverter(repository);

        converter.convert("1");

        verify(repository).getExerciseByIdentifier(1);
    }

}
