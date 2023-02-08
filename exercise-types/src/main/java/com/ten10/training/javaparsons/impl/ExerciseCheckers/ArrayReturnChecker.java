package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.Phase;
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
        Object[] resultArray = (Object[]) result;

        if (resultArray == null) {
            progressReporter.reportError(Phase.RUNNER, "The method is not returning anything");
            return false;
        }
        if (!(resultArray.getClass().equals(answer.getClass()))) {
            progressReporter.reportError(Phase.RUNNER, "Expected return array of type " + answer.getClass());
        }
        if (!(resultArray.length == (answer.length))) {
            progressReporter.reportError(Phase.RUNNER, "Expected return length of " + answer.length);
        }
        return Arrays.equals(resultArray, answer);
    }
}
