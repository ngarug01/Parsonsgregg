package com.ten10.training.javaparsons.webapp.controllers;


import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/exercise/")
public class HelloController {

    @Autowired
    ExerciseRepository exerciseRepository;

    public static class ExerciseInformation {
        private final String url;
        private final String title;
        private final String description;

        ExerciseInformation(String URL, String title, String description) {
            url = URL;
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
        public String getDescription() {
            return description;
        }
    }

    @RequestMapping(value = "getExercises", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ExerciseInformation> geExercises() {
        List<ExerciseInformation> combinedExerciseParameters = new ArrayList<>();
        for (int i = 1; i <= exerciseRepository.getExerciseArraySize(); i++) {
            Exercise exercise = exerciseRepository.getExerciseByIdentifier(i);
            combinedExerciseParameters.add(new ExerciseInformation("exercise/" + i
                ,exercise.getTitle()
                ,exercise.getDescription()));
        }
        return combinedExerciseParameters;
    }

    @RequestMapping(value ="{identifier}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Results postRequestExercise(
        @PathVariable("identifier") Exercise exercise,
        @RequestBody SubmittedSolution submittedSolution) throws Exception {
        Results results = new Results();
        Solution solution = exercise.getSolutionFromUserInput(submittedSolution.getInput(), results);
        solution.evaluate();
        return results;
    }
}
