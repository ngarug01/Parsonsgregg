package com.ten10.training.javaparsons;

import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

@SpringBootApplication
public class Application {

    /**
     * When {@link SpringBootApplication} requires a new {@link JavaCompiler} this method is called to create it.
     *
     * @return a new {@link JavaCompiler}.
     */
    @Bean
    public JavaCompiler javaCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    /**
     * When {@link SpringBootApplication} requires a new {@link SolutionCompiler} this method is called to create it.
     *
     * @param compiler will compile the solution.
     * @return a new {@link SolutionCompiler}.
     */
    @Bean
    public SolutionCompiler solutionCompiler(JavaCompiler compiler) {
        return new JavaSolutionCompiler(compiler);
    }

    /**
     * When {@link SpringBootApplication} requires a new {@link ExerciseRepository} this method is called to create it.
     *
     * @return a new {@link ExerciseRepository}.
     */
    @Bean
    public SolutionRunner solutionRunner() {
        return new ThreadSolutionRunner();
    }

    @Bean
    public ExerciseRepository exerciseRepository(SolutionCompiler compiler, SolutionRunner runner) {

        ExerciseRepositoryImpl repository = new ExerciseRepositoryImpl(compiler, runner);
        repository.addExercise(builder -> builder
            .named("Whole Class \"Hello world\"")
            .checkOutputIs("Hello World!")
            .withExerciseHint("Try including public static Main"));

        repository.addExercise(builder -> builder
            .named("Goodbye Cruel World!")
            .checkOutputIs("Goodbye Cruel World!")
            .withPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .withSuffixCode("}\n}")
            .withExerciseHint("Try using System.out.println()"));


        repository.addExercise(builder -> builder
            .named("Static Field")
            .checkStaticField("x", 3)
            .checkStaticField("y", "hello")
            .withPrefixCode("public class Main { \n")
            .withSuffixCode("public static void main (String[] args) {}}\n")
            .withExerciseHint("Try assigning a value to Int x and String y"));

        repository.addExercise(builder -> builder
            .named("Two Squared")
            .checkReturnValueIs(4)
            .withPrefixCode("public class Main { \npublic static Integer main (String[] args) {")
            .withSuffixCode("}\n}")
            .withExerciseHint("Try using return"));

        repository.addExercise(builder -> builder
            .named("Return Char A")
            .checkReturnValueIs('A')
            .withPrefixCode("public class Main { \npublic static char main (String[] args) {")
            .withSuffixCode("}\n}")
            .withExerciseHint("Try using return with '  '"));

        repository.addExercise(builder -> builder
            .named("Complete the code - Hello World!")
            .checkOutputIs("Hello World!")
            .withPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .withSuffixCode("}\n}")
            .withExerciseHint("Try using System.out.println()"));

        repository.addExercise(builder -> builder
            .named("Use a method called squaresTwo to find the square of 2")
            .checkReturnValueIs(4)
            .withExerciseHint("Name the class squaresTwo with an Integer return")
            .setEntryPoint(ep -> ep
                .className("Methods")
                .methodName("squaresTwo")
                .parameterTypes(new Class<?>[]{String[].class})
                .parameters(new Object[]{new String[]{}})));

        Integer[] outputArray = new Integer[100];
        for (int i = 0; i < 100; i++) {
            outputArray[i] = i + 1;
        }
        repository.addExercise(builder -> builder
            .named("Array tester")
            .checkOutputIsArray(outputArray)
            .withPrefixCode("public class Main { \npublic static String[] main (String[] args) {")
            .withSuffixCode("}\n}")
            .withExerciseHint("int[] x = new int[100];\n" +
                "for(int i = 0; i < 100; i++){"));

        return repository;
    }


    /**
     * Runs the {@link SpringBootApplication} with this {@code class} as a parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
