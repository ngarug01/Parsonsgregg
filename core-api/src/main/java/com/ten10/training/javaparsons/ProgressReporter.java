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

    /**
     * Stores any compiler information in the results object
     *
     * @param message    description of the information
     */

    /**
     * Stores any runtime errors within the results object
     *
     * @param message description of the error
     */

    void reportError(Phase phase, long linenumber, String message);

    void reportInfo(Phase phase, String message);
}
