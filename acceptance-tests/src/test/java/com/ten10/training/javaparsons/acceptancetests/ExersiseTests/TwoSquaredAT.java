package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.TwoSquared;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TwoSquaredAT {
    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private TwoSquared twoSquared = new TwoSquared(driver);
    private String result;

    @BeforeEach
    void beforeEveryTest() {
        twoSquared.goToHomepage();
    }

    @Test
    @Tag("acceptance-tests")
    void twoSquaredInputted() {
        twoSquared.chooseExercise4();
        twoSquared.enterTwoSquaredToInput();
        twoSquared.clickEnterAnswer();
        result = twoSquared.readFromCorrectOutputBox();
        assertTrue(result.contains("Correct answer"));
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        twoSquared.chooseExercise4();
        assertTrue(twoSquared.readFromDescription().contains("Two Squared"));
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }
}
