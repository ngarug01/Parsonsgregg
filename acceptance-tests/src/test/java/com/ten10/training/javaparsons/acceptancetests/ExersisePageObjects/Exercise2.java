package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Exercise2 extends BasePage {
    public Exercise2(WebDriver driver) {
        super(driver);
    }

    private static String URL = "http://localhost:8080/";
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final String GOODBYE_WORLD_CORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Goodbye cruel World!\");}}";
    private static final By ENTER_BUTTON = By.cssSelector("#EnterAnswer");
    private static final By OUTPUT_BOX = By.cssSelector("#output-box");
    private static final By GOODBYE_WORLD_OPTION = By.cssSelector("#ExerciseList > option:nth-child(2)");
    private static final By DESCRIPTION = By.cssSelector("#Description");

    public void enterGoodbyeWorldToInput() {
        findAndType(INPUT_BOX, GOODBYE_WORLD_CORRECT);
    }

    public void clickEnterAnswer() {
        waitAndClick(ENTER_BUTTON);
    }

    public void chooseExercise2() {
        waitAndClick(GOODBYE_WORLD_OPTION);
    }

    public String readFromOutputBox() {
        waitForTextToAppearAtributeValue(OUTPUT_BOX);
        WebElement outputbox = driver.findElement(OUTPUT_BOX);
        return outputbox.getAttribute("value");
    }

    public String readFromDescription() {
        WebElement description = driver.findElement(DESCRIPTION);
        return description.getText();
    }
}
