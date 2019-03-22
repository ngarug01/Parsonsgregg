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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CaptureConsoleOutputTest {

    private CaptureConsoleOutput cco = new CaptureConsoleOutput();
    private static final String LINE_ENDING = System.getProperty("line.separator");
    private static final String SUCCESSFUL_BUILD =
        "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";

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

        @Test
        void correctOutputShouldBeHelloWorld() throws Exception {
            //Arrange
            SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
            ThreadSolutionRunner runner = new ThreadSolutionRunner();
            ProgressReporter progressReporter = mock(ProgressReporter.class);
            HelloWorldSolution helloWorldSolution = new HelloWorldSolution(compiler, runner, SUCCESSFUL_BUILD, progressReporter);
            //Act
            helloWorldSolution.evaluate();
            //Assert
            verify(progressReporter).storeCapturedOutput("Hello World!" + LINE_ENDING);
        }
    }

    @Test
    void flushOutputStream() throws IOException {
        OutputStreamCombiner outputStreamCombiner = mock(OutputStreamCombiner.class);
        cco.start();
        outputStreamCombiner.write('b');
        outputStreamCombiner.flush();
        assertEquals(cco.outputStream.toString(), "");
    }

    @Test
    void closeOutputStream() throws IOException {
        OutputStreamCombiner outputStreamCombiner = mock(OutputStreamCombiner.class);
        cco.start();
        outputStreamCombiner.write('b');
        outputStreamCombiner.close();
        assertEquals(cco.outputStream.toString(), "");
    }

}
