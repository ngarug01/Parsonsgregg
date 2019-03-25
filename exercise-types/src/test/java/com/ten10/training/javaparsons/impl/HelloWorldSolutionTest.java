package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HelloWorldSolutionTest {
    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private static final String LINE_ENDING = System.getProperty("line.separator");
    private static final String SUCCESSFUL_BUILD =
        "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
    private final HelloWorldSolution helloWorldSolution = new HelloWorldSolution(compiler, runner,"userInput string inputted into solution", progressReporter);


    @Test
    void getExerciseReturnsHelloWorldExercise() {
        assertThat(helloWorldSolution.getExercise(), is(instanceOf(HelloWorldExercise.class)));
    }

    @Test
    void checkEvaluate() throws Exception {
        helloWorldSolution.evaluate();
        verify(compiler).compile(helloWorldSolution, progressReporter);
    }

    @Test
    void checkGetFullClassText() {
        assertEquals("userInput string inputted into solution", helloWorldSolution.getFullClassText());
    }

    @Test
    void checkGetClassName() {
        assertEquals("Main", helloWorldSolution.getClassName());
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



