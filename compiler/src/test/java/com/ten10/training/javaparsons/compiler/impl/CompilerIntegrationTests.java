package com.ten10.training.javaparsons.compiler.impl;

import com.ten10.training.javaparsons.ErrorCollector;
import com.ten10.training.javaparsons.compiler.SolutionCompiler.CompilableSolution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
        boolean result = javaSolutionCompiler.compile(data.toSolution(), new ErrorCollector() {
        });

        // Assert
        if (data.shouldPass()) {
            assertTrue(result);
        } else {
            assertFalse(result);
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


        public CompilableSolution toSolution() {
            return new CompilableSolution() {

                @Override
                public CharSequence getFullClassText() {
                    return code;
                }

                @Override
                public String getClassName() {
                    return className;
                }
            };
        }

        public boolean shouldPass() {
            return shouldPass;
        }
    }
}
