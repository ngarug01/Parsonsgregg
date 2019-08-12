package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.CompleteTheCodeExercise;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 3: writing just part of the code.")
class CompleteTheCodeHelloWorldAT {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private CompleteTheCodeExercise completeTheCodeExercise = new CompleteTheCodeExercise(driver);
    private String result;

    @BeforeEach
    void beforeEveryTest() {
        completeTheCodeExercise.goToHomepage();
    }

    @Test
    @Tag("acceptance-tests")
    void helloWorldInputted() {
        completeTheCodeExercise.chooseExercise3();
        completeTheCodeExercise.enterHelloWorldMethodToInput();
        completeTheCodeExercise.clickEnterAnswer();
        result = completeTheCodeExercise.readFromCorrectOutputBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        completeTheCodeExercise.chooseExercise3();
        assertTrue(completeTheCodeExercise.readFromDescription().contains("Complete the Java code"));
    }

    @Test
    @Tag("acceptance-tests")
    void precedingCodeIsDisplayed() {
        completeTheCodeExercise.chooseExercise3();
        result = completeTheCodeExercise.readFromPrecedingCodeBox();
        assertTrue(result.contains("Main"));
    }

    @Test
    @Tag("acceptance-tests")
    void followingCodeIsDisplayed() {
        completeTheCodeExercise.chooseExercise3();
        result = completeTheCodeExercise.readFromFollowingCodeBox();
        assertTrue(result.contains("}"));
    }

    @Test
    @Tag("acceptance-tests")
    void lineNumbersAreTranslated() {
        completeTheCodeExercise.chooseExercise3();
        completeTheCodeExercise.enterIncorrectHelloWorldMethodToInput();
        completeTheCodeExercise.clickEnterAnswer();
        result = completeTheCodeExercise.readFromIncorrectAnswerBox();
        System.out.print(result);
        assertTrue(result.contains("Error on line: 1"));

    }

    @Test
    @Tag("acceptance-tests")
    void timeoutErrorMessageDisplays() {
        completeTheCodeExercise.chooseExercise3();
        completeTheCodeExercise.enterWhileTrueLoop();
        completeTheCodeExercise.clickEnterAnswer();
        result = completeTheCodeExercise.readFromIncorrectAnswerBox();
        System.out.print(result);
        assertTrue(result.contains("timeout error"));

    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }
}




