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
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for Feature 3: 42 (Static Field Exercise)")
public class StaticFieldExerciseAT {

    private static final String CORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int x = 3; \n public static String y=\"hello\"; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int i = 40; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_X_VARIABLE_VALUE_ANSWER = "public class Main{\n public static int x = 2; \n public static String y = \"hello\"; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_VARIABLE_NAMES_ANSWER = "public class Main{\n public static int z = 2; \n public static String w = \"hello\"; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_NOT_STATIC_FIELD_ANSWER = "public class Main{ \n public int x = 3; \n public String y=\"hello\"; \npublic static void main(String[] args){\n}\n}";
    private static final String CORRECT_MULTIPLE_FIELDS_ANSWER = "public class Main{\n public int z = 7; \n static int w = 3; \n public static int x = 3;\n static String a=\"helloo\"; \n public static String y=\"hello\"; \npublic static void main(String[] args){\n}\n}";
    public static final String NAMES_ERROR_MESSAGE = "Either your variables have incorrect access modifiers and/or names, or one isn't there!";
    public static final String NOT_STATIC_ERROR = "your variable(s) need to be static!";
    public static final String X_NOT_INITIALISED_PROPERLY_ERROR = "[The runner error description was: The x variable has not been initialised correctly!]";
    private ExercisePage page;

    public StaticFieldExerciseAT(ChromeDriver driver) {
        this.page = new ExercisePage(driver);
    }


    @BeforeEach
    void goToHomepage() {
        page.goToHomepage();
        page.chooseExercise(3, "Static Field");
    }

    @Test
    @Tag("acceptance-tests")
    void containsCorrectAnswer() {
        page.trySolution(CORRECT_STATIC_FIELD_ANSWER);
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void doesNotContainCorrectAnswer() {
        page.trySolution(INCORRECT_STATIC_FIELD_ANSWER);
        assertFalse(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void incorrectValueErrorMessage() {
        page.trySolution(INCORRECT_X_VARIABLE_VALUE_ANSWER);
        assertEquals(X_NOT_INITIALISED_PROPERLY_ERROR, page.getErrors().toString());

    }

    @Test
    @Tag("acceptance-tests")
    void fieldsNotStaticErrorMessage() {
        page.trySolution(INCORRECT_NOT_STATIC_FIELD_ANSWER);
        assertThat(page.getErrors().toString(), containsString(NOT_STATIC_ERROR));
    }

    @Test
    @Tag("acceptance-tests")
    void irrelevantFieldsIgnored() {
        page.trySolution(CORRECT_MULTIPLE_FIELDS_ANSWER);
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void variableNamesWrongError() {
        page.trySolution(INCORRECT_VARIABLE_NAMES_ANSWER);
        assertThat(page.getErrors().toString(), containsString(NAMES_ERROR_MESSAGE));

    }
}
