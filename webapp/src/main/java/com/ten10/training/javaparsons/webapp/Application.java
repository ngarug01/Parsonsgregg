package com.ten10.training.javaparsons.webapp;

import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
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
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.util.Collections.singletonList;

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
            .setCapturedOutputCheckers(singletonList(new PrintOutChecker("Hello World!")))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Whole Class \"Hello world\"")
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(singletonList(new PrintOutChecker("Goodbye Cruel World!")))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Goodbye Cruel World!")
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(Arrays.asList(
                new StaticFieldValueChecker("Has a static int field x with value 3", "x", 3),
                new StaticFieldValueChecker("Has a static string field y that contains only the word \"hello\" inside it.", "y", "hello")))
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Static Field")
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(singletonList(new ReturnTypeChecker("Returns an int with the value of 2 squared", 4)))
            .setName("Two Squared")
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(singletonList(new ReturnTypeChecker("Returns a Char with value 'A'", 'A')))
            .setName("Return Char A")
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(singletonList(new PrintOutChecker("Hello World!")))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Complete the code - Hello World!")
            .setPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .setSuffixCode("}\n}"));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(singletonList(new ReturnTypeChecker("Returns an int with the value of 2 squared", 4)))
            .setName("Use a method called squaresTwo to find the square of 2")
            .setPrefixCode(null)
            .setSuffixCode(null)
            .setEntryPoint(ep -> ep
                .className("Methods")
                .methodName("squaresTwo")
                .parameterTypesList(new Class<?>[]{String[].class})
                .getParameter(new Object[]{new String[]{}})));
        return repository;
    }


    /**
     * Runs the {@link SpringBootApplication} with this {@code class} as a parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

