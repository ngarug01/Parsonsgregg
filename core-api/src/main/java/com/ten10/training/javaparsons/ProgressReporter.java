package com.ten10.training.javaparsons;

public interface ProgressReporter {

    void storeCapturedOutput (String output);


    void storeCompilerError(long lineNumber, String message);

    void reportCompilerInfo(long lineNumber, String message);

    void setSuccessfulSolution(boolean answer);
}
