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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for feature 7: Two Squared")
public class SquaresTwoAT {

    private static final String TWO_SQUARED_CORRECT = "public class Methods {public static Integer squaresTwo(String[] args) {return 2*2;}}";
    private static final String NOT_SQUARES_TWO_METHOD = "public class Methods {public static Integer method(String[] args) {return 2*2;}}";
    private static final String NOT_METHODS_CLASS = "public class Main {public static Integer squaresTwo(String[] args) {return 2*2;}}";
    private static final String NOT_TWO_SQUARED = "public class Methods {public static Integer squaresTwo(String[] args) {return 4*4;}}";
    private final ExercisePage page;

    public SquaresTwoAT(ChromeDriver driver) {
        this.page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(7, "Use a method called squaresTwo to find the square of 2");
    }

    @Test
    @Tag("acceptance-tests")
    void twoSquaredInputted() {
        page.trySolution(TWO_SQUARED_CORRECT);
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void notTwoSquaredInputted() {
        page.trySolution(NOT_TWO_SQUARED);
        assertThat(page.getErrors(), hasItem(containsString("Expected return of 4")));
        assertFalse(page.isSuccessful());

    }

    @Test
    @Tag("acceptance-tests")
    void notCalledMethodMain() {
        page.trySolution(NOT_SQUARES_TWO_METHOD);
        assertThat(page.getErrors(), hasItem(containsString("No such method squaresTwo")));
        assertFalse(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        assertTrue(page.getDescription().contains("The returned value is 4"));
    }

    @Test
    @Tag("acceptance-tests")
    void notCalledClassMain() {
        page.trySolution(NOT_METHODS_CLASS);
        assertThat(page.getErrors(), hasItem(containsString("class Main is public, should be declared in a file named Main.java")));
        assertFalse(page.isSuccessful());

    }
}
