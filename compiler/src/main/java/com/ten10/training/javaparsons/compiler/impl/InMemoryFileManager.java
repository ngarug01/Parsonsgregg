package com.ten10.training.javaparsons.compiler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;

import static com.ten10.training.javaparsons.compiler.SolutionCompiler.*;

// TODO: Use a full implementation of JavaFileManager
class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFileManager.class);
    private final CompilableSolution solution;

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     * @param solution
     */
    InMemoryFileManager(StandardJavaFileManager fileManager, CompilableSolution solution) {
        super(fileManager);
        this.solution = solution;
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        LOGGER.debug("getFileForInput called with ({}, {}, {})", location, packageName, relativeName);
        return super.getFileForInput(location, packageName, relativeName);
    }

    public InMemoryClassFile getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        LOGGER.debug("getJavaFileForOutput called with ({}, {}, {}, {})",
                location, className, kind, sibling);
        return new InMemoryClassFile(className, solution);
    }
}
