package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class ExerciseBuilderTest {

    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
    private final SolutionRunner runner = mock(SolutionRunner.class);
    private final CreateExercise builder = new CreateExercise(compiler, runner);

    @Test
    void checkStaticField() {
        String varName = "thisIsTheVariableName";
        String value = "thisIsTheValue";

        builder.checkStaticField(varName, value);
        Exercise product = builder.build();
        ExerciseInformation info = product.getInformation();

        String expected = new StringBuilder()
            .append("has a static String field ")
            .append(varName)
            .append(" with a value of ")
            .append(value).toString();
        Assertions.assertEquals(expected, info.getDescription());
    }
}
