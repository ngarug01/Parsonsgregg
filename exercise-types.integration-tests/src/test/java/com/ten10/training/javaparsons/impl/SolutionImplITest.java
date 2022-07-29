package com.ten10.training.javaparsons.impl;

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
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class SolutionImplITest {


    private static SolutionRunner.EntryPoint entryPoint = new EntryPointBuilderImpl()
        .className("Main")
        .methodName("main")
        .parameterTypes(new Class<?>[]{String[].class})
        .parameters(new Object[]{new String[]{}})
        .build();


    private static final String USER_INPUT = "Answer ";
    private SolutionCompiler mockCompiler = mock(SolutionCompiler.class);
    private ThreadSolutionRunner runner = new ThreadSolutionRunner();
    private ThreadSolutionRunner mockRunner = mock(ThreadSolutionRunner.class);
    private PrintOutChecker printOutChecker = mock(PrintOutChecker.class);
    private List<CapturedOutputChecker> capturedOutputCheckers = singletonList(printOutChecker);
    private List<ClassChecker> classCheckers = new ArrayList<>();
    private List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
    private ProgressReporter progressReporter = mock(ProgressReporter.class);
    private final SolutionImpl solutionImpl = new SolutionImpl(mockCompiler, USER_INPUT, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter, new EntryPointBuilderImpl()
        .className("Main")
        .methodName("main")
        .parameterTypes(new Class<?>[]{String[].class})
        .parameters(new Object[]{new String[]{}})
        .build());
    private ClassLoader classLoader = mock(ClassLoader.class);
    //    private SolutionRunner.EntryPoint entryPoint = mock(SolutionRunner.EntryPoint.class);
    private final SolutionRunner.RunResult runResult = mock(SolutionRunner.RunResult.class);
    //    private SolutionRunner.LoadedEntryPoint loadedEntryPoint=entryPoint.load(classLoader);
    private SolutionRunner.LoadedEntryPoint loadedClassRunner = mock(SolutionRunner.LoadedEntryPoint.class);

    SolutionImplITest() throws ClassNotFoundException {
    }

    @BeforeEach
    void setUpMocks() throws InterruptedException, ExecutionException, ReflectiveOperationException {
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
                // .. and invoke the method
                callback.recordCompiledClass(new byte[0]);
                // ... before returning true.
                return true;
            });

        when(runResult.isSuccess()).thenReturn(true);
        when(loadedClassRunner.run(any(SolutionRunner.EntryPoint.class), any(ProgressReporter.class))).thenReturn(runResult);
    }


//    @Test //Test does not work
//    @DisplayName("Calling evaluate should call SolutionCompiler.compile()")
//    void evaluateCallsCompiler() throws Exception {
//        // Act
//        solutionImpl.evaluate();
//        // Assert
//        verify(mockCompiler).compile(solutionImpl, progressReporter);
//    }


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


//    @Test //Test does not work
//    void correctCheckerCalledPrintOut() throws Exception {
//        // Act
//        solutionImpl.evaluate();
//        // Assert
//        verify(printOutChecker).validate("", progressReporter);
//    }

//    @Test //Test does not work
//    void correctCheckerCalledPrintOutReturnsTrue() throws Exception {
//        when(printOutChecker.validate("", progressReporter)).thenReturn(true);
//        assertTrue(solutionImpl.evaluate());
//    }

    @Test
    void earlyReturnWhenCompileFails() throws Exception {
        when(mockCompiler.compile(any(SolutionCompiler.CompilableSolution.class), any(ProgressReporter.class))).thenReturn(false);
        solutionImpl.evaluate();
        verify(loadedClassRunner, never()).run(entryPoint, progressReporter);
    }

    @Test
    void evaluateFailsOnCompileClassNameIncorrect() throws Exception {
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput = "public class ain{\npublic Integer main(String[] args){return 12;}}";
        SolutionImpl solutionImpl = new SolutionImpl(compiler, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter, new EntryPointBuilderImpl()
            .className("Main")
            .methodName("main")
            .parameterTypes(new Class<?>[]{String[].class})
            .parameters(new Object[]{new String[]{}})
            .build());
        solutionImpl.evaluate();
        verify(progressReporter).reportCompilerError(1, "class ain is public, should be declared in a file named ain.java");
    }

    //is an IT
    @Test
    void runTimeFailure() throws Exception {
        //ARRANGE
        SolutionCompiler compiler = new JavaSolutionCompiler(ToolProvider.getSystemJavaCompiler());
        String userInput =
            "public class Main{\npublic String i = \"42\";\npublic static void main(String[] args){while(true){}}}";
        SolutionImpl solutionImpl =
            new SolutionImpl(compiler, userInput, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, progressReporter, new EntryPointBuilderImpl()
                .className("Main")
                .methodName("main")
                .parameterTypes(new Class<?>[]{String[].class})
                .parameters(new Object[]{new String[]{}})
                .build());
        //ACT
        boolean evaluateResult = solutionImpl.evaluate();

        //ASSERT
        assertFalse(evaluateResult, "Infinite Loop should cause runtime error.");
    }
}


