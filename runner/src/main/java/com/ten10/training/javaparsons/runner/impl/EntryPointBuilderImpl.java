package com.ten10.training.javaparsons.runner.impl;

import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPoint;
import com.ten10.training.javaparsons.runner.SolutionRunner.EntryPointBuilder;
//import com.ten10.training.javaparsons.runner.EntryPointBuilder;


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
        private String className;
        private String methodName;
        private Class<?>[] parameterTypes;
        private Object[] parameters;
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
        public ClassLoader getClassLoader() {
            return classLoader;
        }

        EntryPointImpl(EntryPointBuilderImpl builder) {
            className = builder.className;
            methodName = builder.methodName;
            parameterTypes = builder.parameterTypes;
            parameters = builder.parameters;

        }
    }

    static Object returnValue;

    public static Object getReturnValue() {
        return returnValue;

    }

    public static void setReturnValue(Object o) {
        returnValue = o;
    }
}



