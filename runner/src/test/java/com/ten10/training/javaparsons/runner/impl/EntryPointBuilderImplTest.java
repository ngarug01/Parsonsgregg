package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterValidationTests {

    private static SolutionRunner.EntryPointBuilder startEntryPointBuilder = new EntryPointBuilderImpl();

    @Test
    void parameterCheckerLength() {
        //Arrange
        Throwable exception = assertThrows(
            IllegalArgumentException.class, () -> {
                SolutionRunner.EntryPointBuilder entryPointBuilder = startEntryPointBuilder
                    .className(ThreadSolutionRunnerTest.Example.class.getName())
                    .methodName("exampleMethod")
                    .parameterTypes(int.class, boolean.class)
                    .parameters(5);

                //Act
                SolutionRunner.EntryPoint entryPoint = entryPointBuilder.build();
            }
        );

        //Assert
        assertEquals("Exercise parameters invalid (length must match).", exception.getMessage());
    }

    @Test
    void parameterCheckerDataType() {
        //Arrange
        Throwable exception = assertThrows(
            IllegalArgumentException.class, () -> {
                SolutionRunner.EntryPointBuilder entryPointBuilder = startEntryPointBuilder
                    .className(ThreadSolutionRunnerTest.Example.class.getName())
                    .methodName("exampleMethod")
                    .parameterTypes(int.class, boolean.class)
                    .parameters('c' ,5);

                //Act
                SolutionRunner.EntryPoint entryPoint = entryPointBuilder.build();
            }
        );

        //Assert
        assertEquals("Exercise parameters invalid (data type must match).", exception.getMessage());
    }

}
