package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ReturnTypeCheckerTest {
    ReturnTypeChecker returnTypeChecker = new ReturnTypeChecker(4);
    ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void getGoal() {
        assertEquals("the returned value is " + 4, returnTypeChecker.getGoal());
    }

    @Test
    void validate() {
        assertTrue(returnTypeChecker.validate(4, progressReporter));
    }
}
