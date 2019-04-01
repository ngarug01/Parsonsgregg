package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise1;
import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.CompleteTheCodeExercise;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 3: writing just part of the code.")
class CompleteTheCodeHelloWorld {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private Exercise1 exercise1 = new Exercise1(driver);
    private CompleteTheCodeExercise completeTheCodeExercise = new CompleteTheCodeExercise(driver);
    private static ConfigurableApplicationContext ctx;
    private String result;

    @BeforeAll
    static void beforAllTests() {
        ctx = SpringApplication.run(Application.class);
    }


    @BeforeEach
    void beforeEveryTest() {
        exercise1.goToHomepage();
    }

    @Test
    void helloWorldInputted() {
        completeTheCodeExercise.chooseExercise3();
        completeTheCodeExercise.enterHelloWorldMethodToInput();
        completeTheCodeExercise.clickEnterAnswer();
        result = completeTheCodeExercise.readFromCorrectOutputBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    void descriptionChanges() {
        completeTheCodeExercise.chooseExercise3();
        assertTrue(completeTheCodeExercise.readFromDescription().contains("Complete the Java code"));
    }

    @Test
    void precedingCodeIsDisplayed() {
        completeTheCodeExercise.chooseExercise3();
        result = completeTheCodeExercise.readFromPrecedingCodeBox();
        assertTrue(result.contains("Main"));
    }

    @Test
    void followingCodeIsDisplayed() {
        completeTheCodeExercise.chooseExercise3();
        result = completeTheCodeExercise.readFromFollowingCodeBox();
        assertTrue(result.contains("}"));
    }

    @Test
    void lineNumbersAreTranslated() {
        completeTheCodeExercise.chooseExercise3();
        completeTheCodeExercise.enterIncorrectHelloWorldMethodToInput();
        completeTheCodeExercise.clickEnterAnswer();
        result = completeTheCodeExercise.readFromIncorrectAnswerBox();
        System.out.print(result);
        assertTrue(result.contains("Error on line: 1"));

    }

    @AfterAll
    static void afterAllTests() {
        ctx.close();
        driver.quit();
    }
}




