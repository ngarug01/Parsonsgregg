package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.ArrayList;
import java.util.List;

public class Results implements ProgressReporter {

    /**
     * Nested class that sets the structure for information captured in results
     */
    public static class Information {

        private long lineNumber;
        private final String message;

        Information(long lineNumber, String message) {
            this.lineNumber = lineNumber;
            this.message = message;
        }

        Information(String message){
            this.message = message;
        }

        public long getLineNumber() {
            return lineNumber;
        }

        public String getMessage() {
            return message;
        }
    }

    private String output = "";

    private boolean succesfulSolution = false;

    private final List<Information> compilerErrors = new ArrayList<>();
    private final List<Information> compilerInfo = new ArrayList<>();
    private final List<Information> runnerErrors = new ArrayList<>();


    /**
     * @return the output of the submitted code after compilation and running
     */
    public String getOutput() {
        return output;
    }

    /**
     * @return any compiler errors diagnostic logged from submitted code
     */
    public List<Information> getCompilerErrors() {
        return compilerErrors;
    }

    /**
     * @return any Compiler information the diagnostic logged
     */
    public List<Information> getCompilerInfo() {
        return compilerInfo;
    }

    /**
     * @return any runtime errors the diagnostic logged
     */
    public List<Information> getRunnerErrors() {
        return runnerErrors;
    }

    /**
     * Stores the output of the submitted code within the results object
     * @param output of the submitted code
     */
    @Override
    public void storeCapturedOutput(String output) {
        this.output = output;
    }

    /**
     * Stores any compiler errors picked up by the diagnostic in to the results object
     * @param lineNumber of the error
     * @param message description of the error
     */
    @Override
    public void reportCompilerError(long lineNumber, String message) {
        compilerErrors.add(new Information(lineNumber, message));
    }

    /**
     * Stores any compiler information in the results object
     * @param lineNumber of the information
     * @param message description of the information
     */
    @Override
    public void reportCompilerInfo(long lineNumber, String message) {
        compilerInfo.add(new Information(lineNumber, message));
    }

    public void setSuccessfulSolution(boolean answer) {
        succesfulSolution = answer;
    }

    /**
     * stores any runtime errors within the results object
     * @param message description of the error
     */
    @Override
    public void reportRunnerError(String message) {
        runnerErrors.add(new Information(message));
    }

    /**
     * stores whether the solution was correct within the results object
     * @return true if solution is successful
     */
    public boolean isSuccesfulSolution() {
        return succesfulSolution;
    }
}

