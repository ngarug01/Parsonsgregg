package com.ten10.training.javaparsons.webapp;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static java.lang.Integer.parseInt;

@SpringBootApplication
public class Application {


    @Component
    public static class ExerciseConverter implements Converter<String, Exercise> {

        private final ExerciseRepository repository;

        ExerciseConverter(ExerciseRepository repository) {
            this.repository = repository;
        }

        /**
         * if Exercise convert is called the user types a string in to get the exercise they require
         *
         * @param identifier is for the id of the exercise
         *                   the exercise identifier is stored as an integer
         * @return the exercise that has been requested
         */
        @Override
        public Exercise convert(@NonNull String identifier) {
            return repository.getExerciseByIdentifier(parseInt(identifier));
        }
    }

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

    /**
     * Creates an ExerciseRepositoryImpl constructor that takes in a compiler.
     *
     * @param compiler Prepares an user input to be run.
     * @param runner   Used to run a solution
     */
    @Bean
    public ExerciseRepository exerciseRepository(SolutionCompiler compiler, SolutionRunner runner) {

        ExerciseRepositoryImpl repository = new ExerciseRepositoryImpl(compiler, runner);
        repository.addExercise(builder -> builder
            .named("Whole Class \"Hello world\"")
            .checkOutputIs("Hello World!"));

        repository.addExercise(builder -> builder
            .named("Goodbye Cruel World!")
            .checkOutputIs("Goodbye Cruel World!")
            .withPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .withSuffixCode("}\n}"));

        repository.addExercise(builder -> builder
            .named("Static Field")
            .checkStaticField("x", 3)
            .checkStaticField("y", "hello")
            .withPrefixCode("public class Main { \n")
            .withSuffixCode("public static void main(String[] args){}\n}"));
        
        repository.addExercise(builder -> builder
            .named("Two Squared")
            .checkReturnValueIs(4)
            .withPrefixCode("public class Main {public static Integer main(String[] args) {")
            .withSuffixCode("}}"));

        repository.addExercise(builder -> builder
            .named("Return Char A")
            .checkReturnValueIs('A')
            .withPrefixCode("public class Main{public static char main(String[] args){")
            .withSuffixCode("}}"));

        repository.addExercise(builder -> builder
            .named("Complete the code - Hello World!")
            .checkOutputIs("Hello World!")
            .withPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .withSuffixCode("}\n}"));

        repository.addExercise(builder -> builder
            .named("Use a method called squaresTwo to find the square of 2")
            .checkReturnValueIs(4)
            .setEntryPoint(ep -> ep
                .className("Methods")
                .methodName("squaresTwo")
                .parameterTypes(new Class<?>[]{String[].class})
                .parameters(new Object[]{new String[]{}})));

        return repository;
    }


    /**
     * Runs the {@link SpringBootApplication} with this {@code class} as a parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
