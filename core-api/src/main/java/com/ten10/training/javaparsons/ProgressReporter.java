package com.ten10.training.javaparsons;

public interface ProgressReporter {

    /**
     * Stores the output of the submitted code.
     *
     * @param output of the submitted code.
     */
    void storeCapturedOutput(String output);

    /**
     * Stores any compiler errors picked up by the diagnostic.
     *
     * @param lineNumber of the error
     * @param message    description of the error
     */
    default void reportCompilerError(long lineNumber, String message) {
        reportError(Phase.COMPILATION, lineNumber, message);
    }

    /**
     * Stores any compiler information in the results object
     *
     * @param lineNumber of the information
     * @param message    description of the information
     */
    void reportCompilerInfo(long lineNumber, String message);

    /**
     * Stores any runtime errors within the results object
     *
     * @param message description of the error
     */
    void reportRunnerError(String message);

    void reportLoadError(String message);

    void reportError(Phase phase, long linenumber, String message);
}
