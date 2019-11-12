package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

public class ReturnTypeChecker implements MethodReturnValueChecker {

    private final String goal;
    private final Object answer;

    public ReturnTypeChecker(String goal, Object answer) {
        this.answer = answer;
        this.goal = goal;
    }


    @Override
    public String getGoal() {
        return "The returned value is " + answer.toString();
    }

    @Override
    public Boolean validate(Object result, ProgressReporter progressReporter) {
        if (EntryPointBuilderImpl.getReturnValue() == null) {
            progressReporter.reportRunnerError("The method is not returning anything");
            return false;
        }
        if (EntryPointBuilderImpl.getReturnValue().getClass() == answer.getClass()) {
            if (!EntryPointBuilderImpl.getReturnValue().equals(answer)) {
                progressReporter.reportRunnerError("Expected return of " + answer);
            }
        }
        if (EntryPointBuilderImpl.getReturnValue().getClass() != answer.getClass()) {
            progressReporter.reportRunnerError("Return type is incorrect");
        }
        return EntryPointBuilderImpl.getReturnValue().equals(answer);
    }
}
