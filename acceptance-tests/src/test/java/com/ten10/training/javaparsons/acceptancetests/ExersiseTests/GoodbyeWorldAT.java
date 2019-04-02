package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.GoodbyeWorld;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoodbyeWorldAT {
    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private GoodbyeWorld goodbyeWorld = new GoodbyeWorld(driver);
    private String result;

    @BeforeEach
    void beforeEveryTest() {
        goodbyeWorld.goToHomepage();
    }

    @Test
    void goodbyeCruelWorldInputted() {
        goodbyeWorld.chooseExercise2();
        goodbyeWorld.enterGoodbyeWorldToInput();
        goodbyeWorld.clickEnterAnswer();
        result = goodbyeWorld.readFromCorrectOutputBox();
        assertTrue(result.contains("Goodbye Cruel World!"));
    }

    @Test
    void descriptionChanges() {
        goodbyeWorld.chooseExercise2();
        assertTrue(goodbyeWorld.readFromDescription().contains("Goodbye Cruel World!"));
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }
}
