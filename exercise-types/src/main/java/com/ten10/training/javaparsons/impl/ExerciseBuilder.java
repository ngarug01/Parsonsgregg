package com.ten10.training.javaparsons.impl;


import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.function.Consumer;

public interface ExerciseBuilder {

    ExerciseBuilder named(String name);

    ExerciseBuilder checkStaticField(String expectedVariableInput, Object expectedValue);

    ExerciseBuilder checkReturnValueIs(Object expectedReturnValue);

    ExerciseBuilder checkOutputIs(String output);

    ExerciseBuilder checkOutputIsArray(Object[] expectedReturnArray);

    ExerciseBuilder withPrefixCode(String prefixCode);

    ExerciseBuilder withSuffixCode(String suffixCode);

    ExerciseBuilder withExerciseHint(String exerciseHint);

    ExerciseBuilder setEntryPoint(Consumer<SolutionRunner.EntryPointBuilder> entryPointBuilderRunner);
}
