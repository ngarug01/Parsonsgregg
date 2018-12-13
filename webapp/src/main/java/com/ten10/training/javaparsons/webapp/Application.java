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

        @Override
        public Exercise convert(String identifier) {
            return repository.getExerciseByIdentifier(Integer.valueOf(identifier));
        }
    }

    @Bean
    public JavaCompiler javaCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    @Bean
    public SolutionCompiler solutionCompiler(JavaCompiler compiler) {
        return new JavaSolutionCompiler(compiler);
    }

    @Bean
    public ExerciseRepository exerciseRepository(SolutionCompiler compiler) {
        return new ExerciseRepositoryImpl(compiler);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

