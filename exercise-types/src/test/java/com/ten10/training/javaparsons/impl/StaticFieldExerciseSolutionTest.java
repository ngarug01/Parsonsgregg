package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.StaticFieldExerciseSolution;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StaticFieldExerciseSolutionTest {

    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private String userInput = "public class Main{\npublic static int i = 42;\npublic static void main(String[] args){}}";
    private final StaticFieldExerciseSolution staticFieldExerciseSolution = new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

    @Test
    void evaluate() {
        JavaSolutionCompiler jsc = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        ProgressReporter pr = mock(ProgressReporter.class);

        StaticFieldExerciseSolution sFES = new StaticFieldExerciseSolution(jsc, new ThreadSolutionRunner(), userInput, "", pr);
        try {
            sFES.evaluate();
        } catch (Exception e) {
            System.out.println("FAIL");
        }
    }

    @Test
    void checkEvaluate() throws Exception {
        staticFieldExerciseSolution.evaluate();
        verify(compiler).compile(staticFieldExerciseSolution, progressReporter);
    }

    @Test
    void getFullClassText() {
        assertEquals(userInput, staticFieldExerciseSolution.getFullClassText());
    }

    @Test
    void getClassName() {
        assertEquals("Main", staticFieldExerciseSolution.getClassName());
    }

    @Test
    void recordCompiledClass() {

    }

    @Test
    void evaluateFailsOnClassNameIncorrect(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic static int i = 42;\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution = new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        try{
            staticFieldExerciseSolution.evaluate();
            verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void evaluateFailsOnIncorrectAnswer(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class Main{\npublic static int i = 4;\npublic static void main(String[] args){}}";
        StaticFieldExerciseSolution staticFieldExerciseSolution = new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        try{
            staticFieldExerciseSolution.evaluate();
            verify(progressReporter).setSuccessfulSolution(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void evaluatePasses(){
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        StaticFieldExerciseSolution staticFieldExerciseSolution = new StaticFieldExerciseSolution(compiler, runner, userInput, 42, progressReporter);

        try{
            assertTrue(staticFieldExerciseSolution.evaluate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
