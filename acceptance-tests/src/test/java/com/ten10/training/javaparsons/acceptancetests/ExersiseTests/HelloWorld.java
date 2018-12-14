package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exersise1;
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
    private Exersise1 exersise1 = new Exersise1(driver);
    private static ConfigurableApplicationContext ctx;
    private String result;

    @BeforeAll
    static void beforAllTests() {
        ctx = SpringApplication.run(Application.class);
    }


    @BeforeEach
    void beforeEveryTest() {
        exersise1.goToExersise1();
    }

    @Test
    void helloWorldInputted() {
        exersise1.enterHelloWorldToInput();
        exersise1.clickSubmit();
        result = exersise1.readFromCorrectAnswerBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    void semiColonIsMissed () {
        exersise1.enterIncorrectHelloWorldToInput();
        exersise1.clickSubmit();
        result = exersise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Incorrect Answer"));
    }

    @Test
    void notHelloWorld () {
        exersise1.enterNotHelloWorldToInput();
        exersise1.clickSubmit();
        result = exersise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Telly Tubby!"));
    }

    @Test
    void notCalledClassMain () {
        exersise1.enterNotMainClassToInput();
        exersise1.clickSubmit();
        result = exersise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("class ain is public, should be declared in a file named ain.java"));
    }

    @Test
    void notCalledMethodMain () {
        exersise1.enterNotMainMethodToInput();
        exersise1.clickSubmit();
        result = exersise1.readFromIncorrectAnswerBox();
        assertTrue(result.contains("No such method main"));
    }

    @Test
    void informationBoxDisplayed () {
        exersise1.enterHelloWorldInformation();
        exersise1.clickSubmit();
        assertTrue(exersise1.informationBoxDisplayed());
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
        ctx.close();
    }
}


