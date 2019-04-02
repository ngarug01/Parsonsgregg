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
    private String HOST = "localhost:";
    private String PORT;
    private String URL;

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

    /*
    This code is written to enable the dynamic setting of the host in the URL
    Once a system property is able to be set dynamically then this can be implemented
    as for PORT below
     */
    void setHost() {
//        String host = System.getProperty("YOUR_PROPERTY_NAME");
//        if (host.equals("")){
            HOST = "localhost:";
//        } else
//            HOST = host;
    }

    void setPort() {
        String port = System.getProperty("test.server.port");
        if (port.equals("")){
            PORT = "8080";
        } else
            PORT = port;
    }

    void setURL() {
        setHost();
        setPort();
        URL = HOST + PORT;
        System.out.println(URL);
    }

    public void goToHomepage() {
        setURL();
        driver.get(URL);
    }

    void waitForTextToAppearAtributeValue(By selector) {
        wait.until(ExpectedConditions.not(attributeToBe(selector, "value", "")));
    }

    WebElement waitAndGet(By selector){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }


}
