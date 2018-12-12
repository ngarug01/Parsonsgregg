package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Exersise1 extends BasePage {
    public Exersise1(WebDriver driver) {
        super(driver);
    }

    private static String URL = "http://localhost:8080/";
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final String HELLO_WORLD_CORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
    private static final By ENTER_BUTTON = By.cssSelector("#EnterAnswer");
    private static final By OUTPUT_BOX = By.cssSelector("#output-box");


    public void goToExersise1() {
        driver.get(URL);
    }

    public void enterHelloWorldToInput() {
        findAndType(INPUT_BOX, HELLO_WORLD_CORRECT);
    }

    public void clickEnterAnswer() {
        waitAndClick(ENTER_BUTTON);
    }

    public String readFromOutputBox() {
        waitForTextToAppearAtributeValue(OUTPUT_BOX);
        WebElement outputbox = driver.findElement(OUTPUT_BOX);
        return outputbox.getAttribute("value");
    }
}
