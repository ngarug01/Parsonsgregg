package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.StaticFieldExercises;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaticFieldExerciseAT {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private StaticFieldExercises staticFieldExercises = new StaticFieldExercises(driver);
    private String result;


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
        assertTrue(result.contains("Correct answer. Well done!"));
    }

    @Test
    void doesNotContainCorrectAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.incorrectAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromIncorrectAnswerBox();
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
    }
}
