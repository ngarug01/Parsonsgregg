package com.ten10.training.javaparsons.webapp;

//import com.sun.org.apache.bcel.internal.util.Repository;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseBuilder;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.PrintOutChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.ReturnTypeChecker;
import com.ten10.training.javaparsons.impl.ExerciseCheckers.StaticFieldValueChecker;
import com.ten10.training.javaparsons.impl.ExerciseList.CreateExercise;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import com.ten10.training.javaparsons.runner.SolutionRunner;
import com.ten10.training.javaparsons.runner.impl.ThreadSolutionRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {


    @Component
    public static class ExerciseConverter implements Converter<String, Exercise> {

        private final ExerciseRepository repository;

        ExerciseConverter(ExerciseRepository repository) {
            this.repository = repository;
        }

        /** if Exercise convert is called the user types a string in to get the exercise they require
         * @param //identifier is for the id of the exercise
         * the exercise identifier is stored as an integer
         * @return the exercise that has been requested
         */
        @Override
        public Exercise convert(String identifier) {
            return repository.getExerciseByIdentifier(Integer.valueOf(identifier));
        }
    }

    /** When {@link SpringBootApplication} requires a new {@link JavaCompiler} this method is called to create it.
     * @return a new {@link JavaCompiler}.
     */
    @Bean
    public JavaCompiler javaCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    /** When {@link SpringBootApplication} requires a new {@link SolutionCompiler} this method is called to create it.
     * @param //compiler will compile the solution.
     * @return a new {@link SolutionCompiler}.
     */
    @Bean
    public SolutionCompiler solutionCompiler(JavaCompiler compiler) {
        return new JavaSolutionCompiler(compiler);
    }

    /**
     * When {@link SpringBootApplication} requires a new {@link ExerciseRepository} this method is called to create it.
     * @param //compiler will compile the solution.
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
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Whole Class \"Hello world\"")
            .setId(1)
            .setPrefixCode(null)
            .setSuffixCode(null));
//            .build();

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Goodbye Cruel World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Goodbye Cruel World!")
            .setId(2)
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>(Arrays.asList(new StaticFieldValueChecker("Has a static int field with a value of 3 \n", 3))))
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Static Field")
            .setId(3)
            .setPrefixCode(null)
            .setSuffixCode(null));


        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns an int with the value of 2 squared", 4))))
            .setName("Two Squared")
            .setId(4)
            .setPrefixCode(null)
            .setSuffixCode(null));

        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>())
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>(Arrays.asList(new ReturnTypeChecker("Returns a Char with value 'A'", 'A'))))
            .setName("Return Char A")
            .setId(5)
            .setPrefixCode(null)
            .setSuffixCode(null));


        repository.addExercise(builder -> builder
            .setCapturedOutputCheckers(new ArrayList<>(Arrays.asList(new PrintOutChecker("Hello World!"))))
            .setClassCheckers(new ArrayList<>())
            .setMethodReturnValueChecker(new ArrayList<>())
            .setName("Complete the code - Hello World!")
            .setId(6)
            .setPrefixCode("public class Main { \npublic static void main (String[] args) {")
            .setSuffixCode("}\n}"));

        return repository;
    }


    /** Runs the {@link SpringBootApplication} with this {@code class} as a parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

