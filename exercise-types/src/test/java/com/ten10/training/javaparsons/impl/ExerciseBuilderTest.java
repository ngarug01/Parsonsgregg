package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseInformation;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class ExerciseBuilderTest {

    public static final SolutionCompiler COMPILER = mock(SolutionCompiler.class);
    public static final SolutionRunner RUNNER = mock(SolutionRunner.class);
    public static final CreateExercise BUILDER = new CreateExercise(COMPILER, RUNNER);

    @Test @Disabled
    void checkStaticField() {
        String varName = RandomStringUtils.randomAlphabetic(6,12);
        String value = RandomStringUtils.randomAlphabetic(3,6);

        BUILDER.checkStaticField(varName,value);
        Exercise product = BUILDER.build();
        ExerciseInformation info = product.getInformation();
        
        String expected = new StringBuilder()
            .append("has a static String field ")
            .append(varName)
            .append(" with a value of ")
            .append(value).toString();
        Assertions.assertEquals(expected,info.getDescription());
    }
}
