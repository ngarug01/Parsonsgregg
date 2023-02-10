package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;


public class EntryPointBuilderImpl implements EntryPointBuilder {

    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;


    @Override
    public EntryPointBuilder parameterTypes(Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    @Override
    public EntryPointBuilder parameters(Object... parameterList) {
        this.parameters = parameterList;
        return this;
    }

    @Override
    public EntryPointBuilder className(String className) {
        this.className = className;
        return this;
    }

    @Override
    public EntryPointBuilder methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    public EntryPoint build() {
        return new EntryPointImpl(this);
    }


    private class EntryPointImpl implements EntryPoint {
        private final String className;
        private final String methodName;
        private final Class<?>[] parameterTypes;
        private final Object[] parameters;
        private ClassLoader classLoader;

        @Override
        public String getEntryPointClass() {
            return className;
        }

        @Override
        public String getEntryPointMethod() {
            return methodName;
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        @Override
        public Object[] getParameters() {
            return parameters;
        }

        @Override
        public boolean staticMethodsDisallowed() {
            return false;
        }

        @Override
        public ClassLoader getClassLoader() {
            return classLoader;
        }

        EntryPointImpl(EntryPointBuilderImpl builder) {
            className = builder.className;
            methodName = builder.methodName;
            parameterTypes = builder.parameterTypes;
            parameters = builder.parameters;
            validateParameters(parameterTypes, parameters);
        }
    }
    static void validateParameters(Class<?>[] parameterClassArray, Object[] parameterArray) {
        if (parameterClassArray.length != parameterArray.length) {
            throw new IllegalArgumentException("Exercise parameters invalid (length must match).");

        }
        for (int i = 0; i < parameterClassArray.length; i++) {
            if (!validateParameterTypeAndValue(parameterClassArray[i], parameterArray[i])) {
                throw new IllegalArgumentException("Exercise parameters invalid (data type must match).");
            }
        }
    }

    static boolean validateParameterTypeAndValue(Class<?> parameterClass, Object parameterObject) {
        if (parameterClass.equals(boolean.class)) {
            parameterClass = Boolean.class;
        }
        if (parameterClass.equals(byte.class)) {
            parameterClass = Byte.class;
        }
        if (parameterClass.equals(char.class)) {
            parameterClass = Character.class;
        }
        if (parameterClass.equals(short.class)) {
            parameterClass = Short.class;
        }
        if (parameterClass.equals(int.class)) {
            parameterClass = Integer.class;
        }
        if (parameterClass.equals(long.class)) {
            parameterClass = Long.class;
        }
        if (parameterClass.equals(float.class)) {
            parameterClass = Float.class;
        }
        if (parameterClass.equals(double.class)) {
            parameterClass = Double.class;
        }
        return parameterClass.isInstance(parameterObject);
    }
}



