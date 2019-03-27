package com.ten10.training.javaparsons.webapp;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.compiler.SolutionCompiler;
import com.ten10.training.javaparsons.compiler.impl.JavaSolutionCompiler;
import com.ten10.training.javaparsons.impl.ExerciseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

@SpringBootApplication
public class Application {

    @Component
    public static class ExerciseConverter implements Converter<String, Exercise> {

        private final ExerciseRepository repository;

        ExerciseConverter(ExerciseRepository repository) {
            this.repository = repository;
        }

        /** if Exercise convert is called the user types a string in to get the exercise they require
         * @param identifier is for the id of the exercise
         * the exercise identifier is stored as an integer
         * @return the exercise that has been requested
         */
        @Override
        public Exercise convert(String identifier) {
            return repository.getExerciseByIdentifier(Integer.valueOf(identifier));
        }
    }

    /** When springboot requires this method, the javaCompiler is called and this method will tell them how to make it
     * @return the current JavaCompiler
     */
    @Bean
    public JavaCompiler javaCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    /** When springboot requires this method, the solutionCompiler is called and this method will tell them how to make it
     * @param compiler will compile the solution
     * @return the solutionCompiler
     */
    @Bean
    public SolutionCompiler solutionCompiler(JavaCompiler compiler) {
        return new JavaSolutionCompiler(compiler);
    }

    /**
     * When springboot requires this method, the exerciseRepository is called and this method will tell them how to make it
     * @param compiler will compile the solution
     * @return exerciseRepository
     */
    @Bean
    public ExerciseRepository exerciseRepository(SolutionCompiler compiler) {
        return new ExerciseRepositoryImpl(compiler);
    }

    /** will run the Application when SpringApplication is called.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

