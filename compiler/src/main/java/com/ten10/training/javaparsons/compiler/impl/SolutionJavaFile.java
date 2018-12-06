package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class SolutionJavaFile extends SimpleJavaFileObject {

    private final CompilableSolution solution;

    private static URI uriFromSolution(CompilableSolution solution) {
        return URI.create(
                "string:///"
                        + solution.getClassName().replace('.', '/')
                        + Kind.SOURCE.extension);
    }

    SolutionJavaFile(CompilableSolution solution) {
        super(uriFromSolution(solution), Kind.SOURCE);
        this.solution = solution;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return solution.getFullClassText();
    }
}
