package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise4;
import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise1;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TwoSquared {
    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private Exercise1 exersise1 = new Exercise1(driver);
    private Exercise4 exercise4 = new Exercise4(driver);
    private static ConfigurableApplicationContext ctx;
    private String result;

    @BeforeAll
    static void beforeAllTests() {
        ctx = SpringApplication.run(Application.class);
    }


    @BeforeEach
    void beforeEveryTest() {
        exersise1.goToHomepage();
    }

    @Test
    void twoSquaredInputted() {
        exercise4.chooseExercise4();
        exercise4.enterTwoSquaredToInput();
        exercise4.clickEnterAnswer();
        result = exercise4.readFromCorrectOutputBox();
        assertTrue(result.contains("Correct answer"));
    }

    @Test
    void descriptionChanges() {
        exercise4.chooseExercise4();
        assertTrue(exercise4.readFromDescription().contains("Two Squared"));
    }

    @AfterAll
    static void afterAllTests() {
        ctx.close();
        driver.quit();
    }
}
