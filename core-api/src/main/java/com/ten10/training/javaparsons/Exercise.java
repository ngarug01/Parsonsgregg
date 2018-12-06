package com.ten10.training.javaparsons;

/**
 * Objects representing a Parsons Problem Exercise.
 *
 * <p>It should not be necessary to implement this interface, instead fetch instances using
 * {@link ExerciseRepository#getExerciseByIdentifier(int)}.
 */
public interface Exercise extends AutoCloseable {

    /**
     *
     * @return the identifier identifying the exercise.
     */
    int getIdentifier();

    /**
     * Builds and returns a {@link Solution} object based on the provided input. When the {@link Solution#evaluate()}
     * method is called, results will be reported through the {@link ErrorCollector} provided.
     * @param userInput The input provided by the user.
     * @param errorCollector The callback object to use when reporting compilation and test results.
     * @return a {@see Solution} object representing the input provided.
     */
    Solution getSolutionFromUserInput(String userInput, ErrorCollector errorCollector);
}
