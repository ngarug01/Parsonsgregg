package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class CreateExercise implements ExerciseBuilder {

    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final List<CapturedOutputChecker> capturedOutputCheckers = new ArrayList<>();
    private final List<ClassChecker> classCheckers = new ArrayList<>();
    private final List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private String name;
    private int id;
    private String prefixCode;
    private String suffixCode;
    private String exerciseHint;
    private SolutionRunner.EntryPoint entryPoint = new EntryPointBuilderImpl()
        .className("Main")
        .methodName("main")
        .parameterTypes(new Class<?>[]{String[].class})
        .parameters(new Object[]{new String[]{}})
        .build();
    private Consumer<SolutionRunner.EntryPointBuilder> entryPointBuilderRunner;

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

    public String getExerciseHint() {
        return exerciseHint;
    }

    public SolutionRunner.EntryPoint getEntryPoint() {
        return entryPoint;
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
    public ExerciseBuilder checkStaticField(String expectedVariableInput, Object expectedValue) {
        String exerciseDescription = new StringBuilder()
            .append("has a static ")
            .append(expectedValue.getClass().getName().split("^((?!([^.]*)+$).)*")[1])
            .append(" field ")
            .append(expectedVariableInput)
            .append(" with a value of ")
            .append(expectedValue)
            .append(" ").toString();
        this.classCheckers.add(new StaticFieldValueChecker(exerciseDescription, expectedVariableInput, expectedValue));
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

    @Override
    public ExerciseBuilder withExerciseHint(String exerciseHint) {
        this.exerciseHint = exerciseHint;
        return this;
    }

    @Override
    public ExerciseBuilder setEntryPoint(Consumer<SolutionRunner.EntryPointBuilder> entryPointBuilderRunner) {
        EntryPointBuilderImpl builder = new EntryPointBuilderImpl();
        entryPointBuilderRunner.accept(builder);
        this.entryPoint = builder.build();
        return this;
    }

    public CreateExercise setId(int id) {
        this.id = id;
        return this;
    }

    public Exercise build() {
        return new ExerciseImpl(this);
    }
}


