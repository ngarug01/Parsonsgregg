package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.StaticFieldExerciseSolution;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StaticFieldExerciseSolutionIT {


    private SolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());


    @Test
    void evaluateFailsOnClassNameIncorrect() throws Exception {
        //ARRANGE
        String userInput =
            "public class ain{\npublic static int i = 42;\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        verify(progressReporter).reportCompilerError(1,
            "class ain is public, should be declared in a file named ain.java");
        assertFalse(evaluateResult, "Compiler error should return false.");
    }

    @Test
    void evaluateFailsOnIncorrectAnswer() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic static int i = 4;\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Incorrect answer returns false.");
    }

    @Test
    void evaluatePasses() throws Exception {
        //ARRANGE
        String userInput = "public class Main{" +
            " public static int i = 0;" +
            " public static void main(String[] args){" +
            " }" +
            "}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ASSERT
        assertTrue(staticFieldExerciseSolution.evaluate(), "Test should compile, run, and have correct output");
    }

    //When updated to deal with multiple Objects this test needs to be updated
    @Test
    void moreThan1FieldShouldFail() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic static int i = 42;\npublic static String str = \"string\";\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Multiple fields should fail, for now.");
    }

    @Test
    void nonStaticFieldNotRead() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic int i = 42;\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Non static fields should fail the evaluation.");
    }

    @Test
    void stringInsteadOfIntFails() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Answer expects an int but receives a String");
    }

    @Test
    void noFieldsFailsTest() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Having no fields should cause a failure.");
    }

    @Test
    void runTimeFailure() throws Exception {
        //ARRANGE
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){while(true){}}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution =
            new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        //ACT
        boolean evaluateResult = staticFieldExerciseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}
