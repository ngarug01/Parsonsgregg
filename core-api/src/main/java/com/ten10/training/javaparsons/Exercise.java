package com.ten10.training.javaparsons;

public interface Exercise extends AutoCloseable {

    /**
     * @return A valid class name that can be used for compiling and running the test.
     */
    String getClassName();

}
