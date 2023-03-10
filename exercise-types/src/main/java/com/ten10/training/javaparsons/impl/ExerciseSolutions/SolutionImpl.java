package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;
import com.ten10.training.javaparsons.impl.ClassChecker;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.RunResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SolutionImpl implements Solution, SolutionCompiler.CompilableSolution {

    private final EntryPoint entryPoint;

    private final SolutionCompiler compiler;
    private final String userInput;
    private final ProgressReporter progressReporter;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput();
    private byte[] byteCode;
    private final List<CapturedOutputChecker> capturedOutputCheckers;
    private final List<ClassChecker> classCheckers;
    private final List<MethodReturnValueChecker> methodReturnValueCheckers;
    private Object result;
    private Field[] klassFields;
    private SolutionRunner solutionRunner;
    private RunResult runResult;

    /**
     * Creates a new PrintOutExerciseSolution. This constructor sets the local fields.
     *
     * @param compiler         SolutionCompiler to compile the user input.
     * @param userInput        The user input as a String.
     * @param progressReporter ProgressReporter for storing the result of compiling and running the user input.
     */
    public SolutionImpl(SolutionCompiler compiler,
                        String userInput,
                        List<CapturedOutputChecker> capturedOutputCheckers,
                        List<ClassChecker> classCheckers,
                        List<MethodReturnValueChecker> methodReturnValueCheckers,
                        ProgressReporter progressReporter,
                        EntryPoint entryPoint,
                        SolutionRunner solutionRunner) {

        this.compiler = compiler;
        this.userInput = userInput;
        this.capturedOutputCheckers = capturedOutputCheckers;
        this.classCheckers = classCheckers;
        this.methodReturnValueCheckers = methodReturnValueCheckers;
        this.progressReporter = progressReporter;
        this.entryPoint = entryPoint;
        this.solutionRunner = solutionRunner;
    }

    /**
     * Compile and Run the stored user input then compares the output to the expected output.
     *
     * @return True if the output matches, else return False.
     * @throws Exception Exceptions when
     */
    ArrayList<Boolean> results = new ArrayList<>();


    @Override
    public boolean evaluate() throws InvocationTargetException, IllegalAccessException {
        if (!compile()) {
            return false;
        }
        if (!run()) {
            return false;
        }

        for (CapturedOutputChecker checker : capturedOutputCheckers) {
            results.add(checker.validate(output, progressReporter));
        }
        for (ClassChecker checker : classCheckers) {
            if (getClassFields(getClassLoader())) {
                results.add(checker.validate(klassFields, progressReporter));
            }
        }
        if (runResult.hasReturnValue()) {
            Object returnValue = runResult.getReturnValue();
            for (MethodReturnValueChecker checker : methodReturnValueCheckers) {
                results.add(checker.validate(returnValue, progressReporter));
            }
        }
        return !results.contains(false);
    }

    private boolean compile() {
        boolean result = compiler.compile(this, progressReporter);
        assert !result || (byteCode != null);
        return result;
    }

    /**
     * @return The User Input as a CharSequence.
     */
    @Override
    public CharSequence getFullClassText() {
        return userInput;
    }

    /**
     * @return The name of the Class for the solution.
     */
    @Override
    public String getClassName() {
        return entryPoint.getEntryPointClass();
    }

    /**
     * Store the byteCode of a solution. Used by the Runner to run the code.
     *
     * @param byteCode compiled java code.
     */
    @Override
    public void recordCompiledClass(byte[] byteCode) {
        this.byteCode = byteCode;
    }

    private ClassLoader getClassLoader() {

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        return new ClassLoader(contextClassLoader) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (name.equals(getClassName())) {
                    Class<?> clazz = defineClass(name, byteCode, 0, byteCode.length);
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return clazz;
                }
                return super.loadClass(name, resolve);
            }
        };
    }

    private String output = "";


    private boolean run() throws InvocationTargetException, IllegalAccessException {
        captureConsoleOutput.start();
        try {
            return solutionRunner
                .load(entryPoint, getClassLoader(), progressReporter)
                .map(loadedEntryPoint -> loadedEntryPoint.run(progressReporter))
                .map(r -> {
                    runResult = r;
                    return r.ranToCompletion();
                }).orElse(false);
        } finally {
            this.output = captureConsoleOutput.stop();
            progressReporter.storeCapturedOutput(output);
        }
    }

    private boolean getClassFields(ClassLoader classLoader) {
        try {
            Class<?> klass = classLoader.loadClass(entryPoint.getEntryPointClass());
            klassFields = klass.getFields();
            if (klassFields.length != 0) {
                return true;
            } else {
                progressReporter.reportError(Phase.RUNNER, "There is no fields here.");
                results.add(false);
            }
        } catch (ClassNotFoundException e) {
            progressReporter.reportError(Phase.RUNNER, "No such class " + entryPoint.getEntryPointClass());
        }

        return false;
    }

}
