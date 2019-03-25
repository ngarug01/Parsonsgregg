package com.ten10.training.javaparsons.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

class CaptureConsoleOutput {

    ByteArrayOutputStream outputStream;
    private PrintStream old;
    private boolean recording;

    void start() {
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

    String stop() {
        if (!recording) {
            throw new IllegalStateException("stop() called before start()");
        }

        System.setOut(old);

        String capturedValue = outputStream.toString();

        outputStream = null;
        old = null;
        recording = false;

        return capturedValue;
    }

    static class OutputStreamCombiner extends OutputStream {
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
            IOException ioException=null;
            RuntimeException runtimeException = null;
            for (OutputStream os : outputStreams) {
                try {
                    os.flush();
                } catch(IOException e) {
                    ioException=e;
                } catch (RuntimeException e) {
                    runtimeException =e;
                }
            }
            if (null!=ioException){
                throw ioException;
            }
            if (null!=runtimeException){
                throw runtimeException;
            }
        }

        public void close() throws IOException {
            IOException ioException=null;
            RuntimeException runtimeException = null;
            for (OutputStream os : outputStreams) {
                try {
                    os.close();
                } catch(IOException e) {
                    ioException=e;
                } catch (RuntimeException e) {
                    runtimeException =e;
                }
            }
            if (null!=ioException){
                throw ioException;
            }
            if (null!=runtimeException){
                throw runtimeException;
            }
        }
    }
}
