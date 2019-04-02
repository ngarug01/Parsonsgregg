package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StaticFieldExercises extends BasePage {

    public StaticFieldExercises(WebDriver driver) {
        super(driver);
    }

    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final By SELECT_STATIC_FIELD_EXERCISE = By.cssSelector("#ExerciseList > option:nth-child(3)");
    private static final String CORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int i = 42; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_STATIC_FIELD_ANSWER = "public class Main{ \n public static int i = 40; \npublic static void main(String[] args){\n}\n}";
    private static final String INCORRECT_TYPE_STATIC_FIELD_ANSWER = "public class Main{ \n public static String i = \"42\"; \npublic static void main(String[] args){\n}\n}";
    private static final By SUBMIT_ANSWER = By.cssSelector("#enter-answer");
    private static final By CORRECT_ANSWER_BOX = By.cssSelector("#correct-answer");
    private static final By INCORRECT_ANSWER_BOX = By.cssSelector("#incorrect-answer");


    public void selectStaticFieldExercise() {
        waitAndClick(SELECT_STATIC_FIELD_EXERCISE);
    }

    public void correctAnswer() {
        findAndType(INPUT_BOX, CORRECT_STATIC_FIELD_ANSWER);
    }

    public void incorrectAnswer() {
        findAndType(INPUT_BOX, INCORRECT_STATIC_FIELD_ANSWER);
    }

    public void incorrectTypeStaticFieldAnswer() {
        findAndType(INPUT_BOX, INCORRECT_TYPE_STATIC_FIELD_ANSWER);
    }

    public void submitAnswer() {
        waitAndClick(SUBMIT_ANSWER);
    }

    public String readFromCorrectAnswerBox() {
        waitForDivToBeVisible(CORRECT_ANSWER_BOX);
        WebElement correctAnswerBox = driver.findElement(CORRECT_ANSWER_BOX);
        return correctAnswerBox.getText();
    }

    public String readFromIncorrectAnswerBox() {
        waitForDivToBeVisible(INCORRECT_ANSWER_BOX);
        WebElement correctAnswerBox = driver.findElement(INCORRECT_ANSWER_BOX);
        return correctAnswerBox.getText();
    }

}
