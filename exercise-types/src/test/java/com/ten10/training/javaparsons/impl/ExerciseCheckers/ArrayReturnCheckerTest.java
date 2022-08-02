package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ArrayReturnCheckerTest {
    private final Integer[] answer = new Integer[]{1, 2, 3, 4};
    ArrayReturnChecker arrayReturnChecker = new ArrayReturnChecker(answer);
    ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void getGoal() {
        assertEquals("the returned value is " + Arrays.toString(answer), arrayReturnChecker.getGoal());

    }

    @Test
    void validate() {
        EntryPointBuilderImpl.setReturnValue(new Integer[]{1, 2, 3, 4});
        assertTrue(arrayReturnChecker.validate(Arrays.toString(answer), progressReporter));
    }
}