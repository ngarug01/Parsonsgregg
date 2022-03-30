package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;

import java.util.concurrent.ExecutionException;

public class ExercisePathsExerciseSolution implements Solution, SolutionCompiler.CompilableSolution{

    private final SolutionCompiler compiler;
    private final SolutionRunner runner;
    private final String userInput;
    private final String answer;
    private final ProgressReporter progressReporter;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput();
    private byte[] byteCode;

    public ExercisePathsExerciseSolution(SolutionCompiler compiler,
                                    SolutionRunner runner,
                                    String userInput,
                                    String answer,
                                    ProgressReporter progressReporter) throws ClassNotFoundException {

        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.answer = answer;
        this.progressReporter = progressReporter;
    }

    private String output = "";
    private static SolutionRunner.EntryPointBuilder entryPointBuilder = new EntryPointBuilderImpl()
        .className("Main")
        .methodName("method")
        .parameterTypes( new Class<?>[]{String[].class})
        .parameters(new Object[]{new String[]{}});


    private SolutionRunner.EntryPoint entryPoint = entryPointBuilder.build();
    private SolutionRunner.LoadedEntryPoint loadedEntryPoint=entryPoint.load(getClassLoader());
/**
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
**/
    private boolean compile() {
        return compiler.compile(this, progressReporter);
    }

    private boolean canRun(){
        if(byteCode != null) {
            return run();
        }
        return false;
    }

    private boolean run() {
        captureConsoleOutput.start();
        try {
            return loadedEntryPoint.run(entryPoint.getClassLoader(), entryPoint, progressReporter).isSuccess();
        } finally {
            this.output = captureConsoleOutput.stop();
            progressReporter.storeCapturedOutput(output);
        }
    }


    @Override
    public boolean evaluate() {
        if(compile()){
            if(canRun()){
                return output.trim().equals(answer);
            }
        }
        return false;
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
}
