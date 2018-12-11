package com.ten10.training.javaparsons.webapp.controllers;


import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/exercise1")
public class HelloController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public Results postRequest(@RequestBody SubmittedSolution submittedSolution) throws Exception {
        Results results = new Results();
        Exercise exercise = exerciseRepository.getExerciseByIdentifier(1);
        Solution solution = exercise.getSolutionFromUserInput(submittedSolution.getInput(), results);
        solution.evaluate();
        return results;
    }
}
