package com.ten10.training.javaparsons;

import java.util.List;

public interface ProgressReporter {

    /**
     * @return the output of the submitted code after compilation and running.
     */
    String getOutput();

    /**
     * Stores the output of the submitted code.
     * @param output of the submitted code.
     */
    void storeCapturedOutput (String output);

    /**
     * Stores any compiler errors picked up by the diagnostic.
     * @param lineNumber of the error
     * @param message description of the error
     */
    void reportCompilerError(long lineNumber, String message);

    /**
     * Stores any compiler information in the results object
     * @param lineNumber of the information
     * @param message description of the information
     */
    void reportCompilerInfo(long lineNumber, String message);

    /**
     * Set whether the provided solution was correct or not.
     * @param answer {@code True} if successful, {@code False} if unsuccessful.
     */
    void setSuccessfulSolution(boolean answer);

    /**
     * Stores any runtime errors within the results object
     * @param message description of the error
     */
    void reportRunnerError(String message);

    /**
     * @return any compiler errors logged from submitted code
     */
    List<?> getCompilerErrors();

    /**
     * @return any Compiler information the diagnostic logged
     */
    List<?> getCompilerInfo();

    /**
     * @return any runtime errors the diagnostic logged
     */
    List<?> getRunnerErrors();

    /**
     * stores whether the solution was correct within the results object
     * @return true if solution is successful
     */
    boolean isSuccesfulSolution();

}
