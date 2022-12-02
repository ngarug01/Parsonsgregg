package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

public class ReturnTypeChecker implements MethodReturnValueChecker {

    private final Object answer;

    public ReturnTypeChecker(Object answer) {
        this.answer = answer;
    }


    @Override
    public String getGoal() {
        return "the returned value is " + answer;
    }

    @Override
    public Boolean validate(Object result, ProgressReporter progressReporter) {
        if (result == null) {
            progressReporter.reportRunnerError("The method is not returning anything");
            return false;
        }
        if (result.getClass() == answer.getClass()) {
            if (!result.equals(answer)) {
                progressReporter.reportRunnerError("Expected return of " + answer);
                return false;
            }
        } else {
            progressReporter.reportRunnerError("Return type is incorrect");
            return false;
        }
        return true;
    }
}
