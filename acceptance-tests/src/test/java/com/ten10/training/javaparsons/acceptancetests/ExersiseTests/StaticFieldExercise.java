package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.StaticFieldExercises;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaticFieldExercise {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private StaticFieldExercises staticFieldExercises = new StaticFieldExercises(driver);
    private static ConfigurableApplicationContext ctx;
    private String result;


    @BeforeAll
    static void beforeAllTests() {
        ctx = SpringApplication.run(Application.class);
    }

    @BeforeEach
    void goToHomepage() {
        staticFieldExercises.goToHomepage();
    }

    @Test
    void containsCorrectAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.correctAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromCorrectAnswerBox();
        assertTrue(result.contains("42"));
    }

    @Test
    void doesNotContainCorrectAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.incorrectAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromIncorrectAnswerBox();
        assertFalse(result.contains("42"));
        assertTrue(result.contains("Incorrect answer"));
    }

    @Test
    void incorrectTypeAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.incorrectTypeStaticFieldAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromIncorrectAnswerBox();
        assertFalse(result.contains("int"));
        assertTrue(result.contains("Incorrect answer"));
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
        ctx.close();
    }
}
