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
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

}
