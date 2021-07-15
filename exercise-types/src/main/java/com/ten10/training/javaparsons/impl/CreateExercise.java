package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;

import java.util.ArrayList;
import java.util.List;

class CreateExercise implements ExerciseBuilder {

    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final List<CapturedOutputChecker> capturedOutputCheckers = new ArrayList<>();
    private final List<ClassChecker> classCheckers = new ArrayList<>();
    private final List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private String name;
    private String prefixCode;
    private String suffixCode;
    private int id;

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
    public ExerciseBuilder checkOutputIs(String output) {
        this.capturedOutputCheckers.add(new PrintOutChecker(output));
        return this;
    }

    @Override
    public ExerciseBuilder checkReturnValueIs(Object expectedReturnValue) {
        this.methodReturnValueCheckers.add(new ReturnTypeChecker(expectedReturnValue));
        return this;
    }

    @Override
    public ExerciseBuilder named(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ExerciseBuilder checkStaticField(int expectedValue) {
        this.classCheckers.add(new StaticFieldValueChecker("has a static int field with a value of " + (expectedValue), expectedValue));
        return this;
    }

    @Override
    public ExerciseBuilder withPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
        return this;
    }

    @Override
    public ExerciseBuilder withSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise build() {
        return new WholeClassExercise(this);
    }
}
