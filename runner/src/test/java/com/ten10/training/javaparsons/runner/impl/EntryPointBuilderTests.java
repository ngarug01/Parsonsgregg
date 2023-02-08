package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.runner.SolutionRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryPointBuilderTests {

    private static final SolutionRunner.EntryPointBuilder startEntryPointBuilder = new EntryPointBuilderImpl();

    @Test
    void validateParameters() {
        //Arrange
        SolutionRunner.EntryPointBuilder entryPointBuilder = startEntryPointBuilder
            .className(ThreadSolutionRunnerTest.Example.class.getName())
            .methodName("exampleMethod")
            .parameterTypes(boolean.class,
                byte.class,
                char.class,
                short.class,
                int.class,
                long.class,
                float.class,
                double.class)
            .parameters(false,
                (byte)7,
                'c',
                (short)32,
                50,
                89L,
                101F,
                77.7);

        //Act
        SolutionRunner.EntryPoint entryPoint = entryPointBuilder.build();
        Class<?>[] parameterClassArray = entryPoint.getParameterTypes();
        Object[] parameterArray = entryPoint.getParameters();
        for (int i = 0; i < parameterClassArray.length; i++) {
            //Assert
            assertTrue(EntryPointBuilderImpl.validateParameterTypeAndValue(parameterClassArray[i], parameterArray[i]));
        }
    }


    @Test
    void validateParameterLengthException() {
        //Arrange
        Throwable exception = assertThrows(
            IllegalArgumentException.class, () -> {
                SolutionRunner.EntryPointBuilder entryPointBuilder = startEntryPointBuilder
                    .className(ThreadSolutionRunnerTest.Example.class.getName())
                    .methodName("exampleMethod")
                    .parameterTypes(int.class, boolean.class)
                    .parameters(5);

                //Act
                entryPointBuilder.build();
            }
        );

        //Assert
        assertEquals("Exercise parameters invalid (length must match).", exception.getMessage());
    }

    @Test
    void validateParameterDataTypeException() {
        //Arrange
        Throwable exception = assertThrows(
            IllegalArgumentException.class, () -> {
                SolutionRunner.EntryPointBuilder entryPointBuilder = startEntryPointBuilder
                    .className(ThreadSolutionRunnerTest.Example.class.getName())
                    .methodName("exampleMethod")
                    .parameterTypes(int.class, boolean.class)
                    .parameters('c', 5);

                //Act
                entryPointBuilder.build();
            }
        );

        //Assert
        assertEquals("Exercise parameters invalid (data type must match).", exception.getMessage());
    }

}
