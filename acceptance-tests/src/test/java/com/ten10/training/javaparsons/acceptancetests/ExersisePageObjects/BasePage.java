package com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;


public abstract class BasePage {

    WebDriver driver;
    private WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    void waitAndClick(By selector) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selector));
        element.click();
    }

    boolean elementIsVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (ElementNotVisibleException e) {
            return false;
        }
    }

    void findAndType(By elementSelector, String inputString) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementSelector));
        element.sendKeys(inputString);
    }

    void waitForDivToBeVisible(By selector){
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    void waitForTextToAppearAtributeValue(By selector) {
        wait.until(ExpectedConditions.not(attributeToBe(selector, "value", "")));
    }

    WebElement waitAndGet(By selector){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }


}
