package com.ten10.training.javaparsons.impl;

public interface ExerciseBuilder {

    ExerciseBuilder named(String name);
    ExerciseBuilder checkStaticField(int expectedValue);
    ExerciseBuilder checkReturnValueIs(Object expectedReturnValue);
    ExerciseBuilder checkOutputIs(String output);
    ExerciseBuilder withPrefixCode(String prefixCode);
    ExerciseBuilder withSuffixCode(String suffixCode);
}
