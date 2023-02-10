package com.ten10.training.javaparsons;

import java.lang.reflect.InvocationTargetException;

public interface Solution {

    /**
     * Compile and run the solution and return true if successfully compiled.
     */
    boolean evaluate() throws InvocationTargetException, IllegalAccessException;
}
