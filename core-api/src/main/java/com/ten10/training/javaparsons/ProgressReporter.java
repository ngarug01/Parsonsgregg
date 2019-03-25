package com.ten10.training.javaparsons;

import java.util.List;

public interface ProgressReporter {

    void storeCapturedOutput (String output);


    void reportCompilerError(long lineNumber, String message);

    void reportCompilerInfo(long lineNumber, String message);

    void setSuccessfulSolution(boolean answer);

    void reportRunnerError(String message);

    List<?> getCompilerErrors();

    List<?> getCompilerInfo();

    List<?> getRunnerErrors();

}
