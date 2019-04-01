package com.ten10.training.javaparsons.impl.ExerciseList;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;
import com.ten10.training.javaparsons.impl.ClassChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.BaseSolution;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CompleteTheCodeExercise implements Exercise {

    private final String exerciseName;
    private SolutionCompiler compiler;
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private final int id;
    private final String prefixCode;
    private final String suffixCode;
    private int precedingLineNumber;
    private final List<CapturedOutputChecker> capturedOutputCheckers;
    private final List<ClassChecker> classCheckers;
    private final List<MethodReturnValueChecker> methodReturnValueCheckers;

    public CompleteTheCodeExercise(SolutionCompiler compiler, List<CapturedOutputChecker> capturedOutputCheckers, List<ClassChecker> classCheckers, List<MethodReturnValueChecker> methodReturnValueCheckers, String precedingCode, String followingCode, String exerciseName, int id) {
        this.compiler = compiler;
        this.capturedOutputCheckers = capturedOutputCheckers;
        this.classCheckers = classCheckers;
        this.methodReturnValueCheckers = methodReturnValueCheckers;
        this.exerciseName = exerciseName;
        this.id = id;
        this.prefixCode = precedingCode;
        this.suffixCode = followingCode;
        this.precedingLineNumber = StringUtils.countMatches(this.prefixCode, "\n");
    }

    public class LineNumberTranslationProgressReporter extends AbstractProgressReporterDecorator {

        public LineNumberTranslationProgressReporter(ProgressReporter wrapped) {
            super(wrapped);
        }

        @Override
        public void reportCompilerError(long lineNumber, String message) {
            super.reportCompilerError(lineNumber - precedingLineNumber, message);
        }
    }

    String appendCodeToUserInput(String userInput) {
        return prefixCode + userInput + suffixCode;
    }

    @Override
    public int getIdentifier() {
        return id;
    }

    @Override
    public String getTitle() {
        return "Exercise " + getIdentifier() + ": " + exerciseName;
    }

    @Override
    public String getDescription() {
        StringBuilder goals = new StringBuilder();
        goals.append("Complete the Java code so that it; \n");
        for (MethodReturnValueChecker checker : methodReturnValueCheckers) {
            goals.append(checker.getGoal());
        }
        for (ClassChecker checker : classCheckers) {
            goals.append(checker.getGoal());
        }
        for (CapturedOutputChecker checker : capturedOutputCheckers) {
            goals.append(checker.getGoal());
        }
        return goals.toString();
    }

    @Override
    public String getPrecedingCode() {
        return prefixCode;
    }

    @Override
    public String getFollowingCode() {
        return suffixCode;
    }

    @Override
    public Solution getSolutionFromUserInput(String userInput, ProgressReporter progressReporter) {

        return new BaseSolution(compiler, runner, appendCodeToUserInput(userInput), capturedOutputCheckers, classCheckers, methodReturnValueCheckers, new LineNumberTranslationProgressReporter(progressReporter));
    }

    @Override
    public void close() throws Exception {

    }
}
