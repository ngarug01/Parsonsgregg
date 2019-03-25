package com.ten10.training.javaparsons.webapp;
import com.ten10.training.javaparsons.ExerciseRepository;
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

    @Bean
    public JavaCompiler javaCompiler() {
        return ToolProvider.getSystemJavaCompiler();
    }

    @Bean
    public SolutionCompiler solutionCompiler(JavaCompiler compiler) {
        return new JavaSolutionCompiler(compiler);
    }

    @Bean
    public SolutionRunner solutionRunner() {
        return new ThreadSolutionRunner();
    }

    @Bean
    public ExerciseRepository exerciseRepository(SolutionCompiler compiler, SolutionRunner runner) {
        return new ExerciseRepositoryImpl(compiler, runner);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

