package com.ten10.training.javaparsons.runner.impl;

public class ClassInstance {
    private final boolean needed;
    private final Object instance;



    private ClassInstance(boolean needed, Object instance) {
        this.needed = needed;
        this.instance = instance;
    }

    static ClassInstance notNeeded() {
        return new ClassInstance(false, null);
    }

    static ClassInstance neededButNotAvailable() {
        return new ClassInstance(true, null);
    }

    static ClassInstance of(Object instance) {
        return new ClassInstance(true, instance);
    }

    public Object getInstance() {
        return instance;
    }

    public boolean hasFailed() {
        return needed && (instance == null);
    }

}
