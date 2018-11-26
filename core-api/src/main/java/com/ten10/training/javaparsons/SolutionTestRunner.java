package com.ten10.training.javaparsons;

/**
 * Encapsulates the machinery required for evaluating a compiled solution
 *
 * There are different types of exercise that have different sorts of tests, but all will need to be run in an isolated
 * ClassLoader in a new thread, and with a timeout.
 */
public interface SolutionTestRunner {
    boolean runSolution(Solution.CompiledSolution solution, ErrorCollector errorCollector);
}
