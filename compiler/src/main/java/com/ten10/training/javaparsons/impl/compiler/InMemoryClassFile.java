package com.ten10.training.javaparsons.impl.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

class InMemoryClassFile extends SimpleJavaFileObject {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private static URI nameToUri(String name) {
        return URI.create("string:///" + name.replace('.', '/') + Kind.CLASS.extension);
    }

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     *
     */
    InMemoryClassFile(String name) {
        super(nameToUri(name), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }
}
