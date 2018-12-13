package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.ProgressReporter;

import java.util.ArrayList;
import java.util.List;

public class Results implements ProgressReporter {


    public static class Information {

        private final long lineNumber;
        private final String message;

        Information(long lineNumber, String message) {

            this.lineNumber = lineNumber;
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

    public String getOutput() {
        return output;
    }

    public List<Information> getCompilerErrors() {
        return compilerErrors;
    }

    public List<Information> getCompilerInfo() {
        return compilerInfo;
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

    public void setSuccessfulSolution(boolean answer) {
        succesfulSolution = answer;
    }

    public boolean isSuccesfulSolution() {
        return succesfulSolution;
    }
}
