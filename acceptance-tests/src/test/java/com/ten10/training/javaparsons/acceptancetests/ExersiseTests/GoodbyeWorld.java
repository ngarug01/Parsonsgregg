package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise2;
import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise1;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoodbyeWorld {
    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private Exercise1 exercise1 = new Exercise1(driver);
    private Exercise2 exercise2 = new Exercise2(driver);
    private static ConfigurableApplicationContext ctx;
    private String result;

//    @BeforeAll
//    static void beforeAllTests() {
//        ctx = SpringApplication.run(Application.class);
//    }


    @BeforeEach
    void beforeEveryTest() {
        exercise1.goToHomepage();
    }

    @Test
    void goodbyeCruelWorldInputted() {
        exercise2.chooseExercise2();
        exercise2.enterGoodbyeWorldToInput();
        exercise2.clickEnterAnswer();
        result = exercise2.readFromCorrectOutputBox();
        assertTrue(result.contains("Goodbye Cruel World!"));
    }

    @Test
    void descriptionChanges() {
        exercise2.chooseExercise2();
        assertTrue(exercise2.readFromDescription().contains("Goodbye Cruel World!"));
    }

    @AfterAll
    static void afterAllTests() {
        ctx.close();
        driver.quit();
    }
}
