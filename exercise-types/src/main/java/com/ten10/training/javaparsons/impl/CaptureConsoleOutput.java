package com.ten10.training.javaparsons.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class CaptureConsoleOutput {

    private ByteArrayOutputStream outputStream;
    private PrintStream old;
    private boolean recording;

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
