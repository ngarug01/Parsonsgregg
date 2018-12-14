package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class CaptureConsoleOutputTest {

    private CaptureConsoleOutput cco = new CaptureConsoleOutput();
    private static final String LINE_ENDING = System.getProperty("line.separator");
    private static final String SUCCESSFUL_BUILD =
        "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";

    @Test
    void start() {
        cco.start();
        System.out.println("I am a telly tubby developer");
        assertEquals("I am a telly tubby developer"+LINE_ENDING, cco.stop());
    }

    @Test
    void correctOutputShouldBeHelloWorld() throws Exception {
        //Arrange
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        ThreadSolutionRunner runner = new ThreadSolutionRunner();
        ProgressReporter progressReporter = mock(ProgressReporter.class);
        ExerciseSolution exerciseSolution = new ExerciseSolution(compiler, runner, SUCCESSFUL_BUILD, "Hello World!", progressReporter);
        //Act
        exerciseSolution.evaluate();
        //Assert
        verify(progressReporter).storeCapturedOutput("Hello World!" + LINE_ENDING);

    }

}
