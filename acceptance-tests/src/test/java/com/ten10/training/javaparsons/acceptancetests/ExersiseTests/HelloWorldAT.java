package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.HelloWorld;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for feature 1: Running a very simple Program.")
class HelloWorldAT {

    private static DriverFactory driverFactory = new DriverFactory();
    private static WebDriver driver = driverFactory.getDriver();
    private HelloWorld helloWorld = new HelloWorld(driver);
    private String result;


    @BeforeEach
    void beforeEveryTest() {
        helloWorld.goToHomepage();
    }

    @Test
    void helloWorldInputted() {
        helloWorld.enterHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromCorrectAnswerBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    void semiColonIsMissed () {
        helloWorld.enterIncorrectHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Incorrect answer"));
        assertTrue(result.contains("Error on line: 1\n"+"The compiler error description was: ';' expected"));
    }

    @Test
    void notHelloWorld () {
        helloWorld.enterNotHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Telly Tubby!"));
    }

    @Test
    void notCalledClassMain () {
        helloWorld.enterNotMainClassToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("class ain is public, should be declared in a file named ain.java"));
    }

    @Test
    void notCalledMethodMain () {
        helloWorld.enterNotMainMethodToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("No such method main"));
    }

    @Test
    void informationBoxDisplayed () {
        helloWorld.enterHelloWorldInformation();
        helloWorld.clickSubmit();
        assertTrue(helloWorld.informationBoxDisplayed());
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }
}


