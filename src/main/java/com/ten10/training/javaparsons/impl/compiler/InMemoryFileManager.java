package com.ten10.training.javaparsons.impl.compiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

public class InMemoryFileManager<M extends JavaFileManager> extends ForwardingJavaFileManager<M> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFileManager.class);

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    InMemoryFileManager(M fileManager) {
        super(fileManager);
    }

    @Override
    public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
        return super.getFileForInput(location, packageName, relativeName);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        LOGGER.debug("getJavaFileForOutput called with ({}, {}, {}, {})",
                location, className, kind, sibling);
        return new InMemoryClassFile(className);
    }
}
