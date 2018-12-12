package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.Exersise1;
import com.ten10.training.javaparsons.webapp.Application;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
    void Test1() {

        exersise1.enterHelloWorldToInput();
        exersise1.clickEnterAnswer();
        result = exersise1.readFromOutputBox();
        assertEquals("Hello World!\n", result);
    }

    @AfterEach
    void afterEveryTest() {
        driver.quit();
    }

    @AfterAll
    static void afterAllTests() {
        ctx.close();
    }
}


