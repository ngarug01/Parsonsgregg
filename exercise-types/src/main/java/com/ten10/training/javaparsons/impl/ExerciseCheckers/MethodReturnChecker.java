package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;

public class MethodReturnChecker implements MethodReturnValueChecker {

    private final String answer;

    public MethodReturnChecker(String answer) {
        this.answer = answer;
    }




    @Override
    public String getGoal() {
        return answer;
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
            progressReporter.reportRunnerError("Method type is incorrect");
            return false;
        }
        return true;

    }
}
