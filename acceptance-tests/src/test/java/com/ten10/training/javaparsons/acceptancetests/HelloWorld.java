package com.ten10.training.javaparsons.acceptancetests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


class HelloWorld {
    private WebDriver browser;

    @BeforeClass
    void testSetup(){
	    WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver();
    }

    @Before
    public void setup(){
        browser.get("http://training-app.int.thetestpeople.com:8080/");
    }

    @Test
    public void checkHelloWorldPrintedToOutputBox(){
        WebElement inputBox = browser.findElement(By.cssSelector("#input-box"));
        WebElement submitButton = browser.findElement(By.cssSelector("#EnterAnswer"));
        WebElement outputBox = browser.findElement(By.cssSelector("#output-box"));
        inputBox.sendKeys("public class Main\n" +
            "{\n" +
            "    public static void main( String[] args )\n" +
            "    {\n" +
            "        System.out.println(\"Hello World!\");\n" +
            "    }\n" +
            "}");
        submitButton.click();
        assertTrue(outputBox.getText().contains("Hello World!"));
    }

}
