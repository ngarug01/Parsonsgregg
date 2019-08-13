package com.ten10.training.javaparsons.impl.ExerciseSolutions;


import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.apache.commons.lang3.StringUtils;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class ReturnTypeExerciseSolution implements Solution, SolutionCompiler.CompilableSolution, DiagnosticListener<JavaFileObject> {

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
    private final SolutionRunner runner;
    private final String userInput;
    private final Object answer;
    private final ProgressReporter progressReporter;
    private byte[] byteCode;
    private Object output;
    private long currentLineNumber = -1;

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
                                      SolutionRunner runner,
                                      String userInput,
                                      Object answer,
                                      ProgressReporter progressReporter)  {

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
        if (compile()) {
            if (run()) {
                //System.out.println(output + " " + Optional.ofNullable(answer).toString());
                return output.equals(answer);
            }
        }
        return false;
    }

    private boolean compile() {
        boolean result = compiler.compile(this, progressReporter);
        assert !result || byteCode != null;
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

    // Returns True if the code ran to completion, and returned a non-null value.
    // Sets output to the value returned.
    private boolean run() throws InterruptedException, ExecutionException, ReflectiveOperationException {
        SolutionRunner.RunResult result;
        result = runner.run(getClassLoader(), entryPoint, progressReporter);

        if (!result.isSuccess()) {
            return false;
        }
        if (!result.hasReturnValue()) {
            // If the method ran to completion, then this is true when the method wasn't void.
            // For a return value exercise, we can treat void methods as failures.

        progressReporter.reportCompilerError(currentLineNumber, "Method Return Type Should Not Be Void");

            return false;
        }
        output = result.getReturnValue();
        return true;
    }

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        currentLineNumber = diagnostic.getLineNumber();

    }
}
