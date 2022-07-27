package com.ten10.training.javaparsons.webapp;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;

@Component
public class ExerciseConverter implements Converter<String, Exercise> {

    private final ExerciseRepository repository;

    ExerciseConverter(ExerciseRepository repository) {
        this.repository = repository;
    }

    /**
     * if Exercise convert is called the user types a string in to get the exercise they require
     *
     * @param identifier is for the id of the exercise
     *                   the exercise identifier is stored as an integer
     * @return the exercise that has been requested
     */
    @Override
    public Exercise convert(@NonNull String identifier) {
        return repository.getExerciseByIdentifier(parseInt(identifier));
    }
}
