package com.ten10.training.javaparsons.webapp.controllers;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.webapp.views.ExerciseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/exercises/")
public class ExercisesController {

    @Autowired
    ExerciseRepository exerciseRepository;


    /**
     * <p>
     * When {@link org.springframework.boot.autoconfigure.SpringBootApplication} receives a GET request with the
     * value {@code getExercises} this method is called and run.</p>
     * <p>
     * This method gets all stored {@link Exercise} in the {@link ExerciseRepository} as {@link ExerciseDetails}.
     * </p>
     *
     * @return a list of all exercises with the title and description
     */


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ExerciseDetails> getExercises() {
//        List<ExerciseDetails> combinedExerciseParameters = new ArrayList<>();
//        for (Exercise exercise : exerciseRepository) {
//            ExerciseDetails DETAILS = makeExerciseDetails(exercise);
//            combinedExerciseParameters.add(DETAILS
//            );
//        }
//        return combinedExerciseParameters;
        return StreamSupport.stream(exerciseRepository.spliterator(), false)
            .map(this::makeExerciseDetails)
            .collect(Collectors.toList());
    }

    private ExerciseDetails makeExerciseDetails(Exercise exercise) {
        ExerciseInformation info = exercise.getInformation();
        int id = exerciseRepository.getIdentifierFor(exercise);
        return new ExerciseDetails(
            "exercise/" + id, info);
    }
}

