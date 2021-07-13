package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.List;

class CreateExercise implements ExerciseBuilder {


    private SolutionCompiler compiler;
    private SolutionRunner runner;
    private ProgressReporter progressReporter;
    private List<CapturedOutputChecker> capturedOutputCheckers;
    private List<ClassChecker> classCheckers;
    private List<MethodReturnValueChecker> methodReturnValueCheckers;
    private String name;
    private int id;
    private String prefixCode;
    private String suffixCode;

    public CreateExercise(SolutionCompiler compiler, SolutionRunner runner) {
        this.compiler = compiler;
        this.runner = runner;
    }

    public SolutionCompiler getCompiler() {
        return compiler;
    }

    public SolutionRunner getRunner() {
        return runner;
    }

    public ProgressReporter getProgressReporter() {
        return progressReporter;
    }

    public List<CapturedOutputChecker> getCapturedOutputCheckers() {
        return capturedOutputCheckers;
    }

    public List<ClassChecker> getClassCheckers() {
        return classCheckers;
    }

    public List<MethodReturnValueChecker> getMethodReturnValueCheckers() {
        return methodReturnValueCheckers;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPrefixCode() {
        return prefixCode;
    }

    public String getSuffixCode() {
        return suffixCode;
    }


    @Override
    public ExerciseBuilder setCapturedOutputCheckers(List<CapturedOutputChecker> capturedOutputCheckers){
        this.capturedOutputCheckers=capturedOutputCheckers;
        return this;
    }

    @Override
    public ExerciseBuilder setClassCheckers(List<ClassChecker> classCheckers) {
        this.classCheckers=classCheckers;

        return this;
    }

    @Override
    public ExerciseBuilder setMethodReturnValueChecker(List<MethodReturnValueChecker> methodReturnValueCheckers) {
        this.methodReturnValueCheckers=methodReturnValueCheckers;
        return this;

    }

    @Override
    public ExerciseBuilder setProgressReporter(ProgressReporter progressReporter) {
        this.progressReporter=progressReporter;
        return this;
    }

    @Override
    public ExerciseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ExerciseBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ExerciseBuilder setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
        return this;
    }
    @Override
    public ExerciseBuilder setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
        return this;
    }

    public Exercise build() {
        return new WholeClassExercise(this);
    }
}
