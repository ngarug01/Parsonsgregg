package com.ten10.training.javaparsons.impl;


import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.WholeClassExercise;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.List;

public interface ExerciseBuilder {

    ExerciseBuilder setCompiler(SolutionCompiler solutionCompiler);
    ExerciseBuilder setRunner(SolutionRunner solutionRunner);
    ExerciseBuilder setCapturedOutputCheckers(List<CapturedOutputChecker> capturedOutputCheckers);
    ExerciseBuilder setClassCheckers(List<ClassChecker> classCheckers);
    ExerciseBuilder setMethodReturnValueChecker(List<MethodReturnValueChecker> methodValueChecker);
    ExerciseBuilder setProgressReporter(ProgressReporter progressReporter);
    ExerciseBuilder setName(String name);
    ExerciseBuilder setId(int id);
    ExerciseBuilder setPrefixCode(String prefixCode);
    ExerciseBuilder setSuffixCode(String suffixCode);
    WholeClassExercise build();

}





