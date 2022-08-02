package com.ten10.training.javaparsons.acceptancetests.ExerciseTests;

import com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for methods that return arrays")
@ExtendWith(SeleniumExtension.class)
@SingleSession
public class ArrayTesterAT {

    private final String CORRECT_SOLUTION =
        "Integer[] x = new Integer[100];\n" +
            "for (int i = 0; i < 100; i++) {\n" +
            "x[i] = i+1;\n" +
            "}\n" +
            "return x;";

    private final String INCORRECT_ARRAY_LENGTH =
        "Integer[] x = new Integer[99];\n" +
            "for (int i = 0; i < 99; i++) {\n" +
            "x[i] = i+1;\n" +
            "}\n" +
            "return x;";

    private final String INCORRECT_RETURN_TYPE =
        "String[] x = new String[5];\n" +
            "for (int i = 0; i < 5; i++) {\n" +
            "x[i] = \"a\";\n" +
            "}\n" +
            "return x;";

    private final ExercisePage page;

    ArrayTesterAT(ChromeDriver driver) {
        page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(8, "Array Tester");
    }

    @Test
    @Tag("acceptance-tests")
    void integerArrayInputted() {
        page.trySolution(CORRECT_SOLUTION);
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void arrayLengthIncorrect() {
        page.trySolution(INCORRECT_ARRAY_LENGTH);
        assertFalse(page.isSuccessful());
        assertThat(page.getErrors(),
            contains("The runner error description was: Expected return length of 100"));
    }

    @Test
    @Tag("acceptance-tests")
    void incorrectReturnType() {
        page.trySolution(INCORRECT_RETURN_TYPE);
        assertFalse(page.isSuccessful());
        assertThat(page.getErrors(),
            contains("The compiler error description was: incompatible types: java.lang.String[] cannot be converted to java.lang.Integer[]"));
    }
}
