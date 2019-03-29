package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class ReturnTypeExerciseSolution implements Solution, SolutionCompiler.CompilableSolution {

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
    private byte[] byteCode;
    private Object output;

    /**
     * Creates a new ReturnTypeExerciseSolution. This constructor sets the local fields.
     *
     * @param compiler         SolutionCompiler to compile the user input.
     * @param runner           ThreadSolutionRunner to run the compiled code.
     * @param userInput        The user input as a String.
     * @param answer           Expected result of running the user input.
     * @param progressReporter ProgressReporter for storing the result of compiling and running the user input.
     */
    public ReturnTypeExerciseSolution(SolutionCompiler compiler,
                                      ThreadSolutionRunner runner,
                                      String userInput,
                                      Object answer,
                                      ProgressReporter progressReporter) {

        this.compiler = compiler;
        this.runner = runner;
        this.userInput = userInput;
        this.answer = answer;
        this.progressReporter = progressReporter;
    }

    /**
     * Compile and Run the stored user input then compares the output to the expected output.
     *
     * @return True if the output matches, else return False.
     * @throws Exception Exceptions when
     */
    @Override
    public boolean evaluate() throws Exception {
        if(compile()){
            if(canRun()){
                //System.out.println(output + " " + Optional.ofNullable(answer).toString());
                return output.equals(answer);
            }
        }
        return false;
    }

    private boolean canRun() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        if (byteCode != null) {
            if (run() != Optional.empty()) {
                return true;
            }
        }
        return false;
    }

    private boolean compile() {
        return compiler.compile(this, progressReporter);
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
        return "Main";
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

    private Optional<Object> run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        try{
            runner.run(getClassLoader(),entryPoint,progressReporter);
            Object result = runner.getMethodOutput();
            return Optional.ofNullable(result);
        } catch(CancellationException e){
            return Optional.empty();
        } finally {
            this.output = Optional.ofNullable(runner.getMethodOutput()).get();
            progressReporter.storeCapturedOutput(output.toString());
        }
    }
}
