package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class StaticFieldExerciseSolution implements Solution, SolutionCompiler.CompilableSolution {

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
    private final Object answer;
    private final ProgressReporter progressReporter;
    private CaptureConsoleOutput captureConsoleOutput = new CaptureConsoleOutput(); //TODO this dependency should be passed as a parameter in the constructor.
    private byte[] byteCode;
    private Field[] klassFields;

    public StaticFieldExerciseSolution(SolutionCompiler compiler, ThreadSolutionRunner runner, String userInput, Object answer, ProgressReporter progressReporter) {
        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.answer = answer;
        this.progressReporter = progressReporter;
    }


    @Override
    public boolean evaluate() throws Exception {
        boolean canCompile = canCompile();
        boolean canRun = canRun();
        boolean ranToCompletion = canCompile && canRun;
        boolean correctOutput = false;
        if(ranToCompletion){
            if(getClassFields(getClassLoader())){
                correctOutput = evaluateFields();
            }
        }
        progressReporter.setSuccessfulSolution(ranToCompletion && correctOutput);
        return ranToCompletion;
    }

    @Override
    public CharSequence getFullClassText() {
        return userInput;
    }

    @Override
    public String getClassName() {
        return entryPoint.getEntryPointClass();
    }

    @Override
    public void recordCompiledClass(byte[] byteCode) {
        this.byteCode = byteCode;
    }

    /**********PRIVATE HELPER FUNCTIONS**********/


    private boolean canRun() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        if(byteCode != null) {
            if (run() != Optional.empty()) {
                return true;
            }
        }
        return false;
    }

    private boolean canCompile() {
        return compiler.compile(this, progressReporter);
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
        }
    }

    private boolean getClassFields(ClassLoader classLoader) {
        try {
            Class<?> klass = classLoader.loadClass(entryPoint.getEntryPointClass());
            klassFields = klass.getFields();
            if(klassFields.length != 0){ return true; }
        } catch (ClassNotFoundException e) {
            progressReporter.reportRunnerError("No such class "+entryPoint.getEntryPointClass());
        }

        return false;
    }

    private boolean evaluateFields(){
        if(klassFields.length > 1){
            return false;
        }else{
            Field field = klassFields[0];
            field.setAccessible(true);
            try {
                if(field.get(field).equals(answer)){
                    progressReporter.storeCapturedOutput(field.get(field).toString());
                    return true;
                }
            } catch (IllegalAccessException e) {
                progressReporter.reportRunnerError("No access to field: " + field.getName());
            }
        }
        return false;
    }

}
