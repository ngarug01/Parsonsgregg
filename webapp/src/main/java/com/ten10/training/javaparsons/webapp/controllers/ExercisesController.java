package com.ten10.training.javaparsons.webapp.controllers;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ExercisesController {

    @Autowired
    ExerciseRepository exerciseRepository;


    /**
     * <p>
     *     When {@link org.springframework.boot.autoconfigure.SpringBootApplication} receives a GET request with the
     *     value {@code getExercises} this method is called and run.</p>
     * <p>
     *     This method gets all stored {@link Exercise} in the {@link ExerciseRepository} as {@link ExerciseController.ExerciseInformation}.
     * </p>
     * @return a list of all exercises with the title and description
     */

    @RequestMapping(value = "getExercises", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ExerciseController.ExerciseInformation> getExercises() {
        List<ExerciseController.ExerciseInformation> combinedExerciseParameters = new ArrayList<>();
        for (Exercise exercise : exerciseRepository) {
            int id = exercise.getIdentifier();
            combinedExerciseParameters.add(new ExerciseController.ExerciseInformation("exercise/" + id
                ,exercise.getTitle()
                ,exercise.getDescription()
                ,exercise.getPrecedingCode()
                ,exercise.getFollowingCode()
                ,exercise.getDropdownNumber()
            ));

        }
        return combinedExerciseParameters;
    }
}
