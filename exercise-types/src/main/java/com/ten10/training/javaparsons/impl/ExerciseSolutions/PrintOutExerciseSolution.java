package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class PrintOutExerciseSolution implements Solution, SolutionCompiler.CompilableSolution {

    private static SolutionRunner.EntryPoint entryPoint = new SolutionRunner.EntryPoint() {

        @Override
        public String getEntryPointClass() {
            return "Main";
        }

        @Override
        public String getEntryPointMethod() {
            return "main";
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return new Class<?>[]{String[].class};
        }

        @Override
        public Object[] getParameters() {
            return new Object[]{new String[]{}};
        }
    };

    private final SolutionCompiler compiler;
    private final ThreadSolutionRunner runner;
    private final String userInput;
    private final String answer;
    private final ProgressReporter progressReporter;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput();
    private byte[] byteCode;

    public PrintOutExerciseSolution(SolutionCompiler compiler,
                                    ThreadSolutionRunner runner,
                                    String userInput,
                                    String answer,
                                    ProgressReporter progressReporter) {

        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.answer = answer;
        this.progressReporter = progressReporter;
    }

    @Override
    public boolean evaluate() throws Exception {
        boolean cancompile = compile();
        boolean canrun = false;
        if (run() != Optional.empty()) {
            canrun = true;
        }
        boolean ranToCompletion = cancompile && canrun;
        boolean correctOutput = output.contains(answer);
        progressReporter.setSuccessfulSolution(ranToCompletion && correctOutput);
        return ranToCompletion;
    }

    private boolean compile() {
        return compiler.compile(this, progressReporter);
    }

    @Override
    public CharSequence getFullClassText() {
        return userInput;
    }

    @Override
    public String getClassName() {
        return "Main";
    }

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

    private Optional<Object> run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        captureConsoleOutput.start();
        try {
            return runner.run(getClassLoader(), entryPoint, progressReporter);
        } finally {
            this.output = captureConsoleOutput.stop();
            progressReporter.storeCapturedOutput(output);
        }
    }
}
