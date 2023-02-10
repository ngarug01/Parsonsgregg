package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.Phase;
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
            progressReporter.reportError(Phase.RUNNER, "The method is not returning anything");
            return false;
        }
        if (result.getClass() == answer.getClass()) {
            if (!result.equals(answer)) {
                progressReporter.reportError(Phase.RUNNER, "Expected return of " + answer);
                return false;
            }
        } else {
            progressReporter.reportError(Phase.RUNNER, "Method type is incorrect");
            return false;
        }
        return true;

    }
}
