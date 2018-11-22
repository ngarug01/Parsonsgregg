package com.ten10.training.javaparsons.impl.compiler;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.SolutionCompiler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.tools.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CompilerIntegrationTests {

    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    @ParameterizedTest
    @EnumSource(TestData.class)
    void compilerFun(TestData data) {
        // Arrange
        JavaSolutionCompiler javaSolutionCompiler = new JavaSolutionCompiler(compiler);


        // Act
        Optional<SolutionCompiler.CompiledSolution> result = javaSolutionCompiler.compile(data.toSolution(), new ErrorCollector() {
        });

        // Assert
        if (data.shouldPass()) {
            assertTrue(result.isPresent());
        } else {
            assertFalse(result.isPresent());
        }
    }

    public enum TestData {

        WORKING_CLASS("HelloWorld", true, "public class HelloWorld { " +
                "public static void main(String[] args) {" +
                "System.out.println(\"Hello World\");" +
                "}" +
                "}"),
        ERROR_CLASS("HelloWorld", false, "public class HelloWorld { " +
                "public static void main(String[] args) {" +
                "System.out.println(\"Hello World\")" + // Missing Semicolon
                "}" +
                "}");

        private final String className;
        private final boolean shouldPass;
        private final String code;

        TestData(String className, boolean shouldPass, String code) {
            this.className = className;
            this.shouldPass = shouldPass;
            this.code = code;
        }


        public Solution toSolution() {
            return new Solution() {
                @Override
                public Exercise getExercise() {
                    return () -> className;
                }

                @Override
                public CharSequence getFullClassText() {
                    return code;
                }
            };
        }

        public boolean shouldPass() {
            return shouldPass;
        }
    }
}
