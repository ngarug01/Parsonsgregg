package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MethodReturnCheckerTest {

    private final String answer = new String( "class InstanceMethodClass {\n" +
        "public void instanceMethod() {" +
        " int a = 5;" +
        " int b = 10;" +
        " System.out.println(a*b);" +
        "} \n" +
        "}");

    MethodReturnChecker methodReturnChecker = new MethodReturnChecker(answer);
    ProgressReporter progressReporter = mock(ProgressReporter.class);

    @Test
    void getGoal() {
        Assertions.assertEquals( answer, methodReturnChecker.getGoal());
    }

    @Test
    void validate() {

        Assertions.assertTrue(methodReturnChecker.validate(answer,progressReporter));
    }
}
