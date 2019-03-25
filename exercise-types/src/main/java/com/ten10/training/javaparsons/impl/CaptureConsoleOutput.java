package com.ten10.training.javaparsons.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
 * Store all System outs from when start() is called.
 * Returns all System outs from when start() was called after stop() is called.
 */
public class CaptureConsoleOutput {

    private ByteArrayOutputStream outputStream;
    private PrintStream old;
    private boolean recording;

    /**
     * Begins recording everything printed out by the System.
     */
    public void start() {
        if (recording) {
            return;
        }

        recording = true;
        old = System.out;
        outputStream = new ByteArrayOutputStream();

        OutputStream outputStreamCombiner =
            new OutputStreamCombiner(Arrays.asList(old, outputStream));
        PrintStream custom = new PrintStream(outputStreamCombiner);

        System.setOut(custom);
    }

    /**
     * Stop recording the System output and return what has been stored.
     * @return A String of everything outputted to the System since start() was called.
     */
    public String stop() {
        if (!recording) {
            return "stopped";
        }

        System.setOut(old);

        String capturedValue = outputStream.toString();

        outputStream = null;
        old = null;
        recording = false;

        return capturedValue;
    }

    private class OutputStreamCombiner extends OutputStream {
        private List<OutputStream> outputStreams;

        OutputStreamCombiner(List<OutputStream> outputStreams) {
            this.outputStreams = outputStreams;
        }

        public void write(int b) throws IOException {
            for (OutputStream os : outputStreams) {
                os.write(b);
            }
        }

        public void flush() throws IOException {
            for (OutputStream os : outputStreams) {
                os.flush();
            }
        }

        public void close() throws IOException {
            for (OutputStream os : outputStreams) {
                os.close();
            }
        }
    }
}
