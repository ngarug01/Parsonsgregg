package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseList.MethodsStatementsExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MethodsStatementsExerciseTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final MethodsStatementsExercise methodsStatementsExercise = new MethodsStatementsExercise(compiler, "code", "code", "Answer","MY NAME",3);

    @Test

    void methodsStatementsHelloWorldIdentifierIs3() {
        assertEquals(3, methodsStatementsExercise.getIdentifier());
    }

    }


