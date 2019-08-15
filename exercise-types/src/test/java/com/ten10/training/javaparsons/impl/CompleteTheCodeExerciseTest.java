//package com.ten10.training.javaparsons.impl;
//
//import com.ten10.training.javaparsons.ProgressReporter;
//import com.ten10.training.javaparsons.compiler.SolutionCompiler;
//import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
//import com.ten10.training.javaparsons.impl.ExerciseList.CompleteTheCodeExercise;
//import com.ten10.training.javaparsons.impl.ExerciseSolutions.BaseSolution;
//import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
//import org.junit.jupiter.api.Test;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Arrays;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//class CompleteTheCodeExerciseTest {
//    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
//    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
//    private List<CapturedOutputChecker> capturedOutputCheckers = new ArrayList<>(Arrays.asList(new PrintOutChecker("Answer")));
//    private List<ClassChecker> classCheckers = new ArrayList<>();
//    private List<MethodReturnValueChecker> methodReturnValueCheckers = new ArrayList<>();
//    private final CompleteTheCodeExercise completeTheCodeExercise = new CompleteTheCodeExercise(compiler, capturedOutputCheckers, classCheckers, methodReturnValueCheckers, "preceding code", "following code", "MY NAME", 6);
//
//    @Test
//    void methodsStatementsHelloWorldExerciseIdentifierIs6() {
//        assertEquals(6, completeTheCodeExercise.getIdentifier());
//    }
//
//    @Test
//    void getSolutionFromUserInputReturnsBaseSolution() {
//        assertThat(completeTheCodeExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(BaseSolution.class)));
//
//    }
//
//
//    @Test
//    void getTitleOfExercise() {
//        assertEquals(completeTheCodeExercise.getTitle(), "Exercise 6: MY NAME");
//    }
//
//    @Test
//    void getDescription() {
//        assertEquals(completeTheCodeExercise.getDescription(), "Complete the Java code so that it: \nThe program prints out Answer\n");
//    }
//
//    @Test
//    void getPrecedingCode() {
//        assertEquals(completeTheCodeExercise.getPrecedingCode(), "preceding code");
//    }
//
//    @Test
//    void getFollowingCode() {
//        assertEquals(completeTheCodeExercise.getFollowingCode(), "following code");
//    }
//
//    @Test
//    void canWrapProgressReporter(){
//
//        assertThat(completeTheCodeExercise.wrapProgressReporter(progressReporter), is(instanceOf(CompleteTheCodeExercise.LineNumberTranslationProgressReporter.class)));
//
//    }
//
//}
