package com.ten10.training.javaparsons.impl.compiler;

import com.ten10.training.javaparsons.Solution;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class SolutionJavaFile extends SimpleJavaFileObject {

    private final Solution solution;

    private static URI uriFromSolution(Solution solution) {
        return URI.create(
                "string:///"
                        + solution.getExercise().getClassName().replace('.', '/')
                        + Kind.SOURCE.extension);
    }

    SolutionJavaFile(Solution solution) {
        super(uriFromSolution(solution), Kind.SOURCE);
        this.solution = solution;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return solution.getFullClassText();
    }
}
