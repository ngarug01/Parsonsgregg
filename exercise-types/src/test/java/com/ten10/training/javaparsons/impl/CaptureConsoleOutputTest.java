package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.CaptureConsoleOutput.OutputStreamCombiner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

class CaptureConsoleOutputTest {

    private CaptureConsoleOutput cco = new CaptureConsoleOutput();
    private static final String LINE_ENDING = System.getProperty("line.separator");

    @Nested
    @DisplayName("start()")
    class Start {
        @Test
        @DisplayName("should begin recording")
        void start() throws IOException {
            cco.start();
            System.out.println("I am a telly tubby developer");
            assertEquals("I am a telly tubby developer" + LINE_ENDING, cco.stop());
        }

        @Test
        @DisplayName("should not change System.out on a second invocation")
        void startShouldNotChangeSystemOutTwice() {
            cco.start();
            PrintStream ps = System.out;
            cco.start();
            assertEquals(ps, System.out);
        }
    }

    @Nested
    @DisplayName("stop()")
    class Stop {
        @Test
        void stopRecordingWhenNotRecordingShouldThrow() {
            assertThrows(IllegalStateException.class, cco::stop);
        }


    }

    @Test
    void writeOutputStream() throws IOException {
        OutputStream mockOutputStream = mock(OutputStream.class);
        List<OutputStream> listOutputStream = new ArrayList<>();
        listOutputStream.add(mockOutputStream);
        OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
        outputStreamCombiner.write('b');
        verify(mockOutputStream).write('b');
    }

    @Nested
    @DisplayName("flush()")
    class Flush {

        @Test
        void flushOutputStream() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            outputStreamCombiner.flush();
            verify(mockOutputStream).flush();
        }

        @Test
        void flushMultipleOutputStreams() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            outputStreamCombiner.flush();
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

        @Test
        void flushMultipleOutputStreamsWithIOException() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new IOException()).when(mockOutputStream2).flush();
            assertThrows(IOException.class, outputStreamCombiner::flush);
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

        @Test
        void flushMultipleOutputStreamsWithRuntimeException() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new RuntimeException()).when(mockOutputStream2).flush();
            assertThrows(RuntimeException.class, outputStreamCombiner::flush);
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

    }

    @Nested
    @DisplayName("close()")
    class Close {

        @Test
        void closeOutputStream() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            outputStreamCombiner.close();
            verify(mockOutputStream).close();
        }

        @Test
        void CloseMultipleOutputStreams() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            outputStreamCombiner.close();
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }

        @Test
        void closeMultipleOutputStreamsWithIOException() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new IOException()).when(mockOutputStream2).close();
            assertThrows(IOException.class, outputStreamCombiner::close);
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }

        @Test
        void closeMultipleOutputStreamsWithRuntimeException() throws IOException {
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new RuntimeException()).when(mockOutputStream2).close();
            assertThrows(RuntimeException.class, outputStreamCombiner::close);
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }
    }

}
