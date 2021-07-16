package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReturnTypeCheckerTest {
    private int answer = 4;
    ReturnTypeChecker returnTypeChecker = new ReturnTypeChecker(answer);
    ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void getGoal() {
        assertEquals("the returned value is " + answer, returnTypeChecker.getGoal());
    }

    @Test
    void validate() {
        EntryPointBuilderImpl.setReturnValue(4);
        assertTrue(returnTypeChecker.validate(answer, progressReporter));
    }
}
