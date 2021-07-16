package com.ten10.training.javaparsons.impl;


import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import java.util.List;
import java.util.function.Consumer;

public interface ExerciseBuilder {

    ExerciseBuilder setCapturedOutputCheckers(List<CapturedOutputChecker> capturedOutputCheckers);

    ExerciseBuilder setClassCheckers(List<ClassChecker> classCheckers);

    ExerciseBuilder setMethodReturnValueChecker(List<MethodReturnValueChecker> methodValueChecker);

    ExerciseBuilder setProgressReporter(ProgressReporter progressReporter);

    ExerciseBuilder setName(String name);

    ExerciseBuilder setPrefixCode(String prefixCode);

    ExerciseBuilder setSuffixCode(String suffixCode);

    ExerciseBuilder setEntryPoint(Consumer<SolutionRunner.EntryPointBuilder> entryPointBuilderRunner);

}





