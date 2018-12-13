package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Exersise1 extends BasePage {
    public Exersise1(WebDriver driver) {
        super(driver);
    }

    private static String URL = "localhost:8080";
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final String HELLO_WORLD_CORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
    private static final String HELLO_WORLD_INCORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\");}}";
    private static final By ENTER_BUTTON = By.cssSelector("#enter-answer");
    private static final By CORRECT_ANSWER_BOX = By.cssSelector("#correct-answer");
    private static final By INCORRECT_ANSWER_BOX = By.cssSelector("#incorrect-answer");
    private static final By INFORMATION_BOX = By.cssSelector("#information");


    public void goToExersise1() {
        driver.get(URL);
    }

    public void enterHelloWorldToInput() {
        findAndType(INPUT_BOX, HELLO_WORLD_CORRECT);
    }

    public void enterIncorrectHelloWorldToInput() {
        findAndType(INPUT_BOX, HELLO_WORLD_INCORRECT);
    }

    public void clickSubmit() {
        waitAndClick(ENTER_BUTTON);
    }

    public String readFromCorrectAnswerBox() {
        waitForDivToBeVisible(CORRECT_ANSWER_BOX);
        WebElement correctAnswerBox = driver.findElement(CORRECT_ANSWER_BOX);
        return correctAnswerBox.getText();
    }

    public String readFromIncorrectAnswerBox() {
        waitForDivToBeVisible(INCORRECT_ANSWER_BOX);
        WebElement outputbox = driver.findElement(INCORRECT_ANSWER_BOX);
        return outputbox.getAttribute("value");
    }

    public String readFromInformationBox() {
        waitForDivToBeVisible(INFORMATION_BOX);
        WebElement outputbox = driver.findElement(INFORMATION_BOX);
        return outputbox.getAttribute("value");
    }
}
