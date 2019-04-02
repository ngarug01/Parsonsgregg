package com.ten10.training.javaparsons.impl.ExerciseSolutions;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.CapturedOutputChecker;
import com.ten10.training.javaparsons.impl.ClassChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.MethodReturnValueChecker;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.Test;

import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BaseSolutionTest {

    private SolutionCompiler compiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ThreadSolutionRunner mockRunner = mock(ThreadSolutionRunner.class);
    private PrintOutChecker printOutChecker = mock(PrintOutChecker.class);
    private List<CapturedOutputChecker> capturedOutputCheckers = new ArrayList<>(Arrays.asList(printOutChecker));
    private List<ClassChecker> classCheckers = new ArrayList<>();
    private List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final BaseSolution baseSolution = new BaseSolution(compiler, mockRunner, "Answer ", capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
    private SolutionCompiler.CompilableSolution compilableSolution = mock(SolutionCompiler.CompilableSolution.class);
    private ClassLoader classLoader = mock(ClassLoader.class);
    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);


    @Test
    void evaluate() throws Exception {
        baseSolution.evaluate();
        verify(compiler).compile(baseSolution, progressReporter);
    }

    @Test
    void getFullClassText() {
        assertEquals("Answer ", baseSolution.getFullClassText());
    }

    @Test
    void getClassName() {
        assertEquals("Main", baseSolution.getClassName());
    }


    @Test
    void correctCheckerCalledPrintOut() throws Exception {
        when(compiler.compile(baseSolution, progressReporter)).thenReturn(true);
        when(mockRunner.run(classLoader, entryPoint, progressReporter)).thenReturn(Optional.of("SomeObject"));
        when(mockRunner.getMethodOutput()).thenReturn(Optional.of("SomeObject"));
        baseSolution.evaluate();
        verify(printOutChecker).validate("", progressReporter);
    }

    @Test
    void correctCheckerCalledPrintOutReturnsTrue() throws Exception {
        when(compiler.compile(baseSolution, progressReporter)).thenReturn(true);
        when(mockRunner.run(classLoader, entryPoint, progressReporter)).thenReturn(Optional.of("SomeObject"));
        when(mockRunner.getMethodOutput()).thenReturn(Optional.of("SomeObject"));
        when(printOutChecker.validate("", progressReporter)).thenReturn(true);
        assertTrue(baseSolution.evaluate());
    }

    @Test
    void earlyReturnWhenCompileFails() throws Exception {
        when(compiler.compile(compilableSolution, progressReporter)).thenReturn(false);
        baseSolution.evaluate();
        verify(mockRunner, never()).run(classLoader, entryPoint, progressReporter);
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        BaseSolution baseSolution = new BaseSolution(compiler, runner, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
        baseSolution.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    //is an IT
    @Test
    void runTimeFailure() throws Exception {
        //ARRANGE
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){while(true){}}}";
        BaseSolution baseSolution =
            new BaseSolution(compiler, runner, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter);
        //ACT
        boolean evaluateResult = baseSolution.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }


}
