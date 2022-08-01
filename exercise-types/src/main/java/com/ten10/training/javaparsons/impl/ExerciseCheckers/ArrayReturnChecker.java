package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

import java.util.Arrays;

public class ArrayReturnChecker implements MethodReturnValueChecker {


    private final Object[] answer;

    public ArrayReturnChecker(Object[] answer) {
        this.answer = answer;
    }

    @Override
    public String getGoal() {
        return "the returned value is " + Arrays.toString(answer);
    }

    @Override
    public Boolean validate(Object result, ProgressReporter progressReporter) {
        /* TODO: Add in more validation to check array input */
        Object[] resultArray = (Object[]) EntryPointBuilderImpl.getReturnValue();
        if (EntryPointBuilderImpl.getReturnValue() == null) {
            progressReporter.reportRunnerError("The method is not returning anything");
            return false;
        }
        if (!(resultArray.getClass().equals(answer.getClass()))) {
            progressReporter.reportRunnerError("Expected return array of type " + answer.getClass());
        }
        if (!(resultArray.length == (answer.length))) {
            progressReporter.reportRunnerError("Expected return length of " + answer.length);
        }
        return Arrays.equals(resultArray, answer);
    }
}
