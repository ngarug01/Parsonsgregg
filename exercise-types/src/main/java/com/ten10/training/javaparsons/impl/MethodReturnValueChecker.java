package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;

public interface MethodReturnValueChecker {

    String getGoal();

    Boolean validate(String result, ProgressReporter progressReporter);
}
