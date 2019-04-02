package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.HelloWorld;
import org.junit.jupiter.api.*;
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
    @Tag("acceptance-tests")
    void helloWorldInputted() {
        helloWorld.enterHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromCorrectAnswerBox();
        assertTrue(result.contains("Hello World!"));
    }

    @Test
    @Tag("acceptance-tests")
    void semiColonIsMissed () {
        helloWorld.enterIncorrectHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Incorrect answer"));
        assertTrue(result.contains("Error on line: 1\n"+"The compiler error description was: ';' expected"));
    }

    @Test
    @Tag("acceptance-tests")
    void notHelloWorld () {
        helloWorld.enterNotHelloWorldToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Telly Tubby!"));
    }

    @Test
    @Tag("acceptance-tests")
    void notCalledClassMain () {
        helloWorld.enterNotMainClassToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("class ain is public, should be declared in a file named ain.java"));
    }

    @Test
    @Tag("acceptance-tests")
    void notCalledMethodMain () {
        helloWorld.enterNotMainMethodToInput();
        helloWorld.clickSubmit();
        result = helloWorld.readFromIncorrectAnswerBox();
        assertTrue(result.contains("No such method main"));
    }

    @Test
    @Tag("acceptance-tests")
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


