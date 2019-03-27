package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Exercise4 extends BasePage{
    private static final By TWO_SQUARED_OPTION = By.cssSelector("#ExerciseList > option:nth-child(4)");
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final String TWO_SQUARED_CORRECT = "public class Main {public Integer main(String[] args) {return 2*2;}}";
    private static final By SUBMIT_BUTTON = By.cssSelector("#enter-answer");
    private static final By CORRECT_OUTPUT_BOX = By.cssSelector("#correct-answer");
    private static final By DESCRIPTION = By.cssSelector("#Description");

    public Exercise4(WebDriver driver) {
        super(driver);
    }

    public void chooseExercise4() {
        waitAndClick(TWO_SQUARED_OPTION);
    }

    public void enterTwoSquaredToInput() {
        findAndType(INPUT_BOX, TWO_SQUARED_CORRECT);
    }

    public void clickEnterAnswer() {
        waitAndClick(SUBMIT_BUTTON);
    }

    public String readFromCorrectOutputBox() {
        waitForDivToBeVisible(CORRECT_OUTPUT_BOX);
        WebElement outputbox = driver.findElement(CORRECT_OUTPUT_BOX);
        return outputbox.getText();    }

    public String readFromDescription() {
        WebElement description = driver.findElement(DESCRIPTION);
        return description.getText();    }
}
