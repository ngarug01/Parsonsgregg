//package com.ten10.training.javaparsons.impl;
//
//import com.ten10.training.javaparsons.ProgressReporter;
//import com.ten10.training.javaparsons.compiler.SolutionCompiler;
//import com.ten10.training.javaparsons.impl.ExerciseSolutions.StaticFieldExerciseSolution;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.mock;
//
//class StaticFieldExerciseTest {
//
//    private final SolutionCompiler compiler = mock(SolutionCompiler.class);
//    private final StaticFieldExercise staticFieldExercise = new StaticFieldExercise(compiler, 42, "NAME", 1, "Get a thing");
//    private final ProgressReporter progressReporter = mock(ProgressReporter.class);
//
//    @Test
//    void getIdentifier() {
//        assertEquals(1, staticFieldExercise.getIdentifier());
//    }
//
//    @Test
//    void getTitleOfExercise() {
//        assertEquals("Exercise 1: NAME", staticFieldExercise.getTitle());
//    }
//
//    @Test
//    void getDescription() {
//        assertEquals("Get a thing NAME", staticFieldExercise.getDescription());
//    }
//
//    @Test
//    void getSolutionFromUserInput() throws Exception {
//        assertThat(staticFieldExercise.getSolutionFromUserInput("", progressReporter), is(instanceOf(StaticFieldExerciseSolution.class)));
//    }
//
//}
