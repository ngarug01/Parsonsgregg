package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.CompleteTheCodeExercise;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 3: writing just part of the code.")
@ExtendWith(SeleniumExtension.class)
@SingleSession
class CompleteTheCodeHelloWorldAT {

    private final WebDriver driver;
    private final CompleteTheCodeExercise completeTheCodeExercise;
    private String result;

    CompleteTheCodeHelloWorldAT(ChromeDriver driver) {
        this.driver = driver;
        completeTheCodeExercise = new CompleteTheCodeExercise(driver);
    }

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
}




