package com.ten10.training.javaparsons.impl.compiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;

// TODO: Use a full implementation of JavaFileManager
class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFileManager.class);

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    InMemoryFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        LOGGER.debug("getFileForInput called with ({}, {}, {})", location, packageName, relativeName);
        return super.getFileForInput(location, packageName, relativeName);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        LOGGER.debug("getJavaFileForOutput called with ({}, {}, {}, {})",
                location, className, kind, sibling);
        return new InMemoryClassFile(className);
    }
}
