package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exercise1;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 1: Running a very simple Program.")
class HelloWorld {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private Exercise1 exercise1 = new Exercise1(driver);
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
        exercise1.enterHelloWorldToInput();
        exercise1.clickSubmit();
        result = exercise1.readFromCorrectAnswerBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    void semiColonIsMissed () {
        exercise1.enterIncorrectHelloWorldToInput();
        exercise1.clickSubmit();
        result = exercise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Incorrect answer"));
        assertTrue(result.contains("Error on line: 1\n"+"The compiler error description was: ';' expected"));
    }

    @Test
    void notHelloWorld () {
        exercise1.enterNotHelloWorldToInput();
        exercise1.clickSubmit();
        result = exercise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Telly Tubby!"));
    }

    @Test
    void notCalledClassMain () {
        exercise1.enterNotMainClassToInput();
        exercise1.clickSubmit();
        result = exercise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("class ain is public, should be declared in a file named ain.java"));
    }

    @Test
    void notCalledMethodMain () {
        exercise1.enterNotMainMethodToInput();
        exercise1.clickSubmit();
        result = exercise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("No such method main"));
    }

    @Test
    void informationBoxDisplayed () {
        exercise1.enterHelloWorldInformation();
        exercise1.clickSubmit();
        assertTrue(exercise1.informationBoxDisplayed());
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
        ctx.close();
    }
}


