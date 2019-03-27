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
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    public static class ExerciseInformation {
        private final String url;
        private final String title;
        private final String description;
        private final String precedingCode;
        private final String followingCode;

        ExerciseInformation(String URL, String title, String description, String precedingCode, String followingCode) {
            url = URL;
            this.title = title;
            this.description = description;
            this.precedingCode = precedingCode;
            this.followingCode = followingCode;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
        public String getDescription() { return description;}
        public String getPrecedingCode() { return precedingCode;}
        public String getFollowingCode() { return followingCode;}

    }

    @RequestMapping(value = "getExercises", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ExerciseInformation> getExercises() {
        List<ExerciseInformation> combinedExerciseParameters = new ArrayList<>();
        for (int i = 1; i <= exerciseRepository.getExerciseArraySize(); i++) {
            Exercise exercise = exerciseRepository.getExerciseByIdentifier(i);
            combinedExerciseParameters.add(new ExerciseInformation("exercise/" + i
                ,exercise.getTitle()
                ,exercise.getDescription()
                ,exercise.getPrecedingCode()
                ,exercise.getFollowingCode()
            ));

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
