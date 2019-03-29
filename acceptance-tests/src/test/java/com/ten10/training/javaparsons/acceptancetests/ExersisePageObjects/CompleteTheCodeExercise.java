package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompleteTheCodeExercise extends BasePage {
    public CompleteTheCodeExercise(WebDriver driver) {
        super(driver);
    }

    private static String URL = "http://localhost:8080/";
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final String HELLO_WORLD_METHOD_CORRECT = "System.out.println(\"Hello World!\");";
    private static final String HELLO_WORLD_METHOD_INCORRECT = "System.out.println(\"Hello World!\")";
    private static final By ENTER_BUTTON = By.cssSelector("#enter-answer");
    private static final By PRECEDING_CODE_BOX = By.cssSelector("#preceding-code-box");
    private static final By FOLLOWING_CODE_BOX = By.cssSelector("#following-code-box");
    private static final By CORRECT_OUTPUT_BOX = By.cssSelector("#correct-answer");
    private static final By COMPLETE_THE_CODE_HELLO_WORLD = By.cssSelector("#ExerciseList > option:nth-child(6)");
    private static final By DESCRIPTION = By.cssSelector("#Description");
    private static final By INCORRECT_ANSWER_BOX = By.cssSelector("#incorrect-answer");

    public void enterHelloWorldMethodToInput() {
        findAndType(INPUT_BOX, HELLO_WORLD_METHOD_CORRECT);
    }

    public void enterIncorrectHelloWorldMethodToInput() {
        findAndType(INPUT_BOX, HELLO_WORLD_METHOD_INCORRECT);
    }

    public void clickEnterAnswer() {
        waitAndClick(ENTER_BUTTON);
    }

    public void chooseExercise3() {
        waitAndClick(COMPLETE_THE_CODE_HELLO_WORLD);
    }

    public String readFromCorrectOutputBox() {
        waitForDivToBeVisible(CORRECT_OUTPUT_BOX);
        WebElement outputbox = driver.findElement(CORRECT_OUTPUT_BOX);
        return outputbox.getText();
    }

    public String readFromDescription() {
        WebElement description = driver.findElement(DESCRIPTION);
        return description.getText();
    }

    public String readFromPrecedingCodeBox() {
        WebElement description = driver.findElement(PRECEDING_CODE_BOX);
        return description.getText();
    }

    public String readFromFollowingCodeBox() {
        WebElement description = driver.findElement(FOLLOWING_CODE_BOX);
        return description.getText();
    }

    public String readFromIncorrectAnswerBox() {
        waitForDivToBeVisible(INCORRECT_ANSWER_BOX);
        WebElement outputbox = driver.findElement(INCORRECT_ANSWER_BOX);
        return outputbox.getText();
    }
}
