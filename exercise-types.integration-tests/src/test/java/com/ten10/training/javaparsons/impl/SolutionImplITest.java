package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.Phase;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseSolutions.SolutionImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.EntryPointBuilderImpl;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolutionImplITest {


    public static final SolutionCompiler SOLUTION_COMPILER = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
    private static final SolutionRunner.EntryPoint ENTRY_POINT = new EntryPointBuilderImpl()
        .className("Main")
        .methodName("main")
        .parameterTypes(String[].class)
        .parameters(new Object[]{new String[]{}})
        .build();


    private static final String USER_INPUT = "Answer ";
    private final SolutionCompiler mockCompiler = mock(SolutionCompiler.class);
    private final ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private final PrintOutChecker printOutChecker = mock(PrintOutChecker.class);
    private final List<CapturedOutputChecker> capturedOutputCheckers = singletonList(printOutChecker);
    private final List<ClassChecker> classCheckers = new ArrayList<>();
    private final List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final SolutionImpl solutionImpl = new SolutionImpl(mockCompiler,
        USER_INPUT,
        capturedOutputCheckers,
        classCheckers,
        methodReturnValueCheckers,
        progressReporter,
        ENTRY_POINT,
        runner);
    private final SolutionRunner.RunResult runResult = mock(SolutionRunner.RunResult.class);
    private final SolutionRunner.LoadedEntryPoint loadedClassRunner = mock(SolutionRunner.LoadedEntryPoint.class);

    SolutionImplITest() {
    }

    @BeforeEach
    void setUpMocks() {
        // By default, compiling and running methods solutions should be successful

        // The contract of SolutionCompiler.compile() is that the callback method recordCompiledClass will be called.
        // We therefore need to do this more advanced stubbing to get hold of the callback object, and invoke the
        // method before returning true.
        //
        // When we call compile on the solution compiler...
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class)))
            // ... then ...
            .thenAnswer((Answer<Boolean>) invocationOnMock -> {
                // ... Get the callback object
                SolutionCompiler.CompilableSolution callback = invocationOnMock.getArgument(0);
                // ... and invoke the method
                callback.recordCompiledClass(new byte[0]);
                // ... before returning true.
                return true;
            });

        when(runResult.ranToCompletion()).thenReturn(true);
        when(loadedClassRunner.run(any(ProgressReporter.class))).thenReturn(runResult);
    }


    @Test
    @DisplayName("Calling evaluate should call SolutionCompiler.compile()")
    void evaluateCallsCompiler() {
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class))).thenReturn(false);
        // Act
        solutionImpl.evaluate();
        // Assert
        verify(mockCompiler).compile(solutionImpl, progressReporter);
    }

    @Test
    @DisplayName("Calling getFullClassText() should return the provided text")
        // TODO: Should build the text from template!
    void getFullClassText() {
        assertEquals(USER_INPUT, solutionImpl.getFullClassText());
    }

    @Test
    void getClassName() {  // TODO: Should probably get this from the entrypoint!
        assertEquals("Main", solutionImpl.getClassName());
    }

    @Test //Test does not work
    void correctCheckerCalledPrintOut() {
        String userInput =
            "public class Main{\n" +
                "public String i = \"42\";\n" +
                "public static void main(String[] args){{}}}";
        SolutionImpl solutionImpl =
            new SolutionImpl(SOLUTION_COMPILER,
                userInput,
                capturedOutputCheckers,
                classCheckers,
                methodReturnValueCheckers,
                progressReporter,
                ENTRY_POINT,
                runner);
        // Act
        solutionImpl.evaluate();
        // Assert
        verify(printOutChecker).validate("", progressReporter);
    }

    @Test
    void correctCheckerCalledPrintOutReturnsTrue() {
        when(printOutChecker.validate("", progressReporter)).thenReturn(true);
        String userInput =
            "public class Main{\n" +
                "public String i = \"42\";\n" +
                "public static void main(String[] args){{}}}";
        SolutionImpl solutionImpl =
            new SolutionImpl(SOLUTION_COMPILER,
                userInput,
                capturedOutputCheckers,
                classCheckers,
                methodReturnValueCheckers,
                progressReporter,
                ENTRY_POINT,
                runner);
        assertTrue(solutionImpl.evaluate());
    }

    @Test
    void earlyReturnWhenCompileFails() {
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class))).thenReturn(false);
        solutionImpl.evaluate();
        verify(loadedClassRunner, never()).run(progressReporter);
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() {
        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        SolutionImpl solutionImpl = new SolutionImpl(SOLUTION_COMPILER,
            userInput,
            capturedOutputCheckers,
            classCheckers,
            methodReturnValueCheckers,
            progressReporter,
            ENTRY_POINT,
            runner);
        solutionImpl.evaluate();
        verify(progressReporter).reportError(Phase.COMPILER, 1, "class ain is public, should be declared in a file named ain.java");
    }

    //is an IT
    @Test
    void runTimeFailure() {
        //ARRANGE
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){while(true){}}}";
        SolutionImpl solutionImpl =
            new SolutionImpl(SOLUTION_COMPILER,
                userInput,
                capturedOutputCheckers,
                classCheckers,
                methodReturnValueCheckers,
                progressReporter,
                ENTRY_POINT,
                runner);
        //ACT
        boolean evaluateResult = solutionImpl.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}


