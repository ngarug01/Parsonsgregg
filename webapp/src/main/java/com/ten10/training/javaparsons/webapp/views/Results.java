package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;

import java.util.ArrayList;
import java.util.List;

public class Results implements ProgressReporter {

    public List<Information> getLoadErrors() {
        return loadErrors;
    }

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

        Information(String message) {
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

    private boolean successfulSolution = false;

    private final List<Information> compilerErrors = new ArrayList<>();
    private final List<Information> compilerInfo = new ArrayList<>();
    private final List<Information> runnerErrors = new ArrayList<>();

    private final List<Information> loadErrors = new ArrayList<>();

    public String getOutput() {
        return output;
    }

    public List<Information> getCompilerErrors() {
        return compilerErrors;
    }

    public List<Information> getCompilerInfo() {
        return compilerInfo;
    }

    public List<Information> getRunnerErrors() {
        return runnerErrors;
    }

    @Override
    public void storeCapturedOutput(String output) {
        this.output = output;
    }

    @Override
    public void reportCompilerError(long lineNumber, String message) {
        compilerErrors.add(new Information(lineNumber, message));
    }

    @Override
    public void reportCompilerInfo(long lineNumber, String message) {
        compilerInfo.add(new Information(lineNumber, message));
    }

    /**
     * Set whether the provided solution was correct or not.
     *
     * @param answer {@code True} if successful, {@code False} if unsuccessful.
     */
    public void setSuccessfulSolution(boolean answer) {
        successfulSolution = answer;
    }

    @Override
    public void reportRunnerError(String message) {
        runnerErrors.add(new Information(message));
    }

    @Override
    public void reportLoadError(String message) {
        loadErrors.add(new Information( message ));
    }

    @Override
    public void reportError(Phase phase, long linenumber, String message) {
        switch (phase){
            case LOADER:
                loadErrors.add(new Information(message));
                break;
            case RUNNER:
                runnerErrors.add(new Information(message));
                break;
            case COMPILER:
                compilerErrors.add(new Information(linenumber, message));
                break;
        }
    }

    @Override
    public void reportError(Phase phase, long linenumber, String message) {
        switch (phase) {
            case LOADER:
                loadErrors.add(new Information(message));
                break;
            case RUNNER:
                runnerErrors.add(new Information(message));
                break;
            case COMPILER:
                compilerErrors.add(new Information(linenumber, message));
                break;
        }
    }

    public boolean isSuccessfulSolution() {
        return successfulSolution;
    }
}

//Future Dev Academy - all Javadoc is in the ProgressReporter Interface
