package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ExercisePage extends BasePage {

    // Locators
    private static final By EXERCISE_LIST = By.cssSelector("#ExerciseList");
    private static final By INPUT_BOX = By.cssSelector("#input-box");
    private static final By SUBMIT_BUTTON = By.cssSelector("#enter-answer");
    private static final By OUTPUT_BOX = By.id("runner-output");
    private static final By CORRECT_ANSWER_BOX = By.id("correct-answer");
    private static final By INCORRECT_BOX = By.id("incorrect-answer");
    private static final By INFORMATION_BOX = By.id("information");
    private static final By DESCRIPTION = By.id("Description");


    // Conditions
    private static final ExpectedCondition<WebElement> OUTPUT_BOX_IS_VISIBLE
        = ExpectedConditions.visibilityOfElementLocated(OUTPUT_BOX);
    private static final ExpectedCondition<WebElement> INCORRECT_BOX_IS_VISIBLE
        = ExpectedConditions.visibilityOfElementLocated(INCORRECT_BOX);
    private static final ExpectedCondition<WebElement> INFORMATION_BOX_IS_VISIBLE
        = ExpectedConditions.visibilityOfElementLocated(INFORMATION_BOX);


    public ExercisePage(WebDriver driver) {
        super(driver);
    }

    private void waitUntilLoadFinished() {
        WebElement loadingElement;
        try {
            loadingElement = driver.findElement(By.id("loading"));
        } catch (NoSuchElementException e) {
            return;
        }
        wait.until(ExpectedConditions.stalenessOf(loadingElement));
    }


    public void chooseExcercise(int exerciseNumber, String exerciseTitle) {
        String expectedText = "Exercise " + exerciseNumber + ": " + exerciseTitle;
        new Select(driver.findElement(EXERCISE_LIST)).selectByVisibleText(expectedText);
    }

    private void clearSolutionBox() {
        driver.findElements(INPUT_BOX).clear();
    }

    private void typeSolution(String solution) {
        driver.findElement(INPUT_BOX).sendKeys(solution);
    }

    private void submitSolution() {
        driver.findElement(SUBMIT_BUTTON).click();
        waitUntilLoadFinished();
    }

    public void trySolution(String solution) {
        clearSolutionBox();
        typeSolution(solution);
        submitSolution();
    }

    public String getOutput() {
        WebElement outputBox = wait.until(OUTPUT_BOX_IS_VISIBLE);
        return outputBox.getText();
    }

    public boolean isSuccessful() {
        return driver.findElement(CORRECT_ANSWER_BOX).isDisplayed();
    }

    public List<String> getErrors() {
        // TODO: Our current page just makes work for us here.
        String text = wait.until(INCORRECT_BOX_IS_VISIBLE).getText();
        final String prefix = "Incorrect answer\n";
        assert text.startsWith(prefix);
        text = text.substring(prefix.length());
        String[] lines = text.split("\\R");
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            // Exclude lines that just state the line number of the following line
            if (line.matches("^Error on line:\\s+\\d+$")) {
                continue;
            }
            result.add(line);
        }
        return result;
    }

    public String getInfo() {
        WebElement informationBox = wait.until(INFORMATION_BOX_IS_VISIBLE);
        return informationBox.getText();
    }

    public String getDescription() {
        return driver.findElement(DESCRIPTION).getText();
    }

    @Override
    public void goToHomepage() {
        super.goToHomepage();
        waitUntilLoadFinished();
    }
}
