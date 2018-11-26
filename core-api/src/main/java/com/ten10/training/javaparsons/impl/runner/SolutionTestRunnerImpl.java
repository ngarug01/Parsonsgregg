package com.ten10.training.javaparsons.impl.runner;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.SolutionTestRunner;

public class SolutionTestRunnerImpl implements SolutionTestRunner {
    @Override
    public boolean runSolution(Solution.CompiledSolution solution, ErrorCollector errorCollector) {
        Exercise exercise = solution.getExercise();
        return false;


    }
}
