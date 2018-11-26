package com.ten10.training.javaparsons;

import java.util.Optional;

/**
 * Encapsulates the machinery for dealing with a compiler, and provides a simple mechanism for compiling an exercise.
 */
public interface SolutionCompiler {

    /**
     * Compile the solution, reporting any errors or warnings to the errorCollector.
     * Return a CompiledSolution on success.
     *
     * @param solution The submitted solution to be compiled. This will not be modified.
     * @param errorCollector An object which will collect errors. This is modified
     * @return a CompiledSolution upon success
     * @throws NullPointerException if either parameter is null
     */
    Optional<Solution.CompiledSolution> compile(Solution solution, ErrorCollector errorCollector);




}
