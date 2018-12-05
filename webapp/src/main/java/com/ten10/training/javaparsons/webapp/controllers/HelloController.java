package com.ten10.training.javaparsons.webapp.controllers;


import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/exercise1")
public class HelloController {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public Results postRequest(@RequestBody SubmittedSolution submittedSolution) {
        System.out.println(submittedSolution);
        return new Results() ;
    }
}
