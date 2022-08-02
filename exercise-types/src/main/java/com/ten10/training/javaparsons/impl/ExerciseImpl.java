package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.LineNumberTranslationProgressReporter;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.SolutionImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

import java.util.List;

import static java.util.Objects.isNull;

class ExerciseImpl implements Exercise {
    private final String exerciseName;
    private final String exerciseHints;
    private String prefixCode;
    private String suffixCode;
    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final int id;
    private final List<CapturedOutputChecker> capturedOutputCheckers;
    private final List<ClassChecker> classCheckers;
    private final List<MethodReturnValueChecker> methodReturnValueCheckers;
    private SolutionRunner.EntryPoint entryPoint = new EntryPointBuilderImpl()
        .className("Main")
        .methodName("main")
        .parameterTypes(new Class<?>[]{String[].class})
        .parameters(new Object[]{new String[]{}})
        .build();


    /**
     * Creates a new ExerciseImpl.
     *
     * @param compiler     Prepares an user input to be run.
     * @param runner       Runs the solution
     * @param exerciseName Description of the exercise.
     * @param id           The unique identifier of an exercise.
     */
    public ExerciseImpl(SolutionCompiler compiler,
                        SolutionRunner runner,
                        List<CapturedOutputChecker> capturedOutputCheckers,
                        List<ClassChecker> classCheckers,
                        List<MethodReturnValueChecker> methodReturnValueCheckers,
                        String exerciseName,
                        int id,
                        String prefixCode,
                        String suffixCode,
                        String exerciseHints) {
        this.compiler = compiler;
        this.runner = runner;
        this.exerciseName = exerciseName;
        this.id = id;
        this.capturedOutputCheckers = capturedOutputCheckers;
        this.classCheckers = classCheckers;
        this.methodReturnValueCheckers = methodReturnValueCheckers;
        this.prefixCode = prefixCode;
        this.suffixCode = suffixCode;
        this.exerciseHints = exerciseHints;
    }

    public ExerciseImpl(CreateExercise buildedExercise) {
        this.compiler = buildedExercise.getCompiler();
        this.runner = buildedExercise.getRunner();
        this.exerciseName = buildedExercise.getName();
        this.id = buildedExercise.getId();
        this.capturedOutputCheckers = buildedExercise.getCapturedOutputCheckers();
        this.classCheckers = buildedExercise.getClassCheckers();
        this.methodReturnValueCheckers = buildedExercise.getMethodReturnValueCheckers();
        this.prefixCode = buildedExercise.getPrefixCode();
        this.suffixCode = buildedExercise.getSuffixCode();
        this.prefixCode = normalizePrefixCode(buildedExercise.getPrefixCode());
        this.suffixCode = normalizeSuffixCode(buildedExercise.getSuffixCode());
        this.exerciseHints = buildedExercise.getExerciseHint();
        this.entryPoint = buildedExercise.getEntryPoint();


    }

    private static String normalizePrefixCode(String contextCode) {
        if (isNull(contextCode)) return null;
        contextCode = contextCode.trim();
        if (contextCode.isEmpty()) return null;
        return String.join(System.lineSeparator(), contextCode.split("\\R")) + System.lineSeparator();
    }

    private static String normalizeSuffixCode(String suffixCode) {
        suffixCode = normalizePrefixCode(suffixCode);
        return isNull(suffixCode) ? null : System.lineSeparator() + suffixCode;
    }


    public String returnAppendedUserInput(String userInput) {
        if (prefixCode != null && suffixCode != null) {
            return prefixCode + userInput + suffixCode;
        } else {
            return userInput;
        }

    }

    @Override
    public ExerciseInformation getInformation() {
        return new ExerciseInformation() {

            @Override
            public String getPrecedingCode() {

                return prefixCode;
            }

            @Override
            public String getFollowingCode() {

                return suffixCode;
            }

            @Override
            public String getExerciseHint() {
                return exerciseHints;
            }

            /**
             * @return The unique identifier of an exercise.
             */
            @Override
            public Integer getIdentifier() {
                return id;
            }

            /**
             * @return A string containing the name of the exercise.
             */
            @Override
            public String getTitle() {
                return "Exercise " + getIdentifier() + ": " + exerciseName;
            }


            /**
             * @return A string containing the description and instruction of an exercise.
             */

            @Override
            public String getDescription() {
                StringBuilder goals = new StringBuilder();
                goals.append("Create a Java class that: \n");
                for (MethodReturnValueChecker checker : methodReturnValueCheckers) {
                    goals.append(checker.getGoal());
                }
                for (ClassChecker checker : classCheckers) {
                    goals.append(checker.getDesc());
                }
                for (CapturedOutputChecker checker : capturedOutputCheckers) {
                    goals.append(checker.getGoal());
                }
                return goals.toString();
            }
        };
    }

    /**
     * @param userInput        The input provided by the user.
     * @param progressReporter The callback object to use when reporting compilation and test results.
     * @return A new PrintOutExerciseSolution from user input.
     */
    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {
        if (null != prefixCode) {
            progressReporter = new LineNumberTranslationProgressReporter(prefixCode, progressReporter);
        }
        return new SolutionImpl(compiler,
            returnAppendedUserInput(userInput),
            capturedOutputCheckers,
            classCheckers,
            methodReturnValueCheckers,
            progressReporter,
            entryPoint,
            runner);
    }
}

