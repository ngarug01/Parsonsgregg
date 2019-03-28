package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise1;
import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise3;
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
    private Exercise3 exercise3 = new Exercise3(driver);
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
        exercise3.chooseExercise3();
        exercise3.enterHelloWorldMethodToInput();
        exercise3.clickEnterAnswer();
        result = exercise3.readFromCorrectOutputBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    void descriptionChanges() {
        exercise3.chooseExercise3();
        assertTrue(exercise3.readFromDescription().contains("Complete the Java code"));
    }

    @Test
    void precedingCodeIsDisplayed() {
        exercise3.chooseExercise3();
        result = exercise3.readFromPrecedingCodeBox();
        assertTrue(result.contains("Main"));
    }

    @Test
    void followingCodeIsDisplayed() {
        exercise3.chooseExercise3();
        result = exercise3.readFromFollowingCodeBox();
        assertTrue(result.contains("}"));
    }

    @Test
    void lineNumbersAreTranslated() {
        exercise3.chooseExercise3();
        exercise3.enterIncorrectHelloWorldMethodToInput();
        exercise3.clickEnterAnswer();
        result = exercise3.readFromIncorrectAnswerBox();
        System.out.print(result);
        assertTrue(result.contains("Error on line: 1"));

    }

    @AfterAll
    static void afterAllTests() {
        ctx.close();
        driver.quit();
    }
}




