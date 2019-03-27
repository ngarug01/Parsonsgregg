package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;


import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class DriverFactory {

    static {
        ChromeDriverManager.chromedriver().setup();
    }

    private WebDriver driver;

    WebDriver getDriver() {
        if (driver == null) {
            setDriver();
        }
        return driver;
    }

    private void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

}
