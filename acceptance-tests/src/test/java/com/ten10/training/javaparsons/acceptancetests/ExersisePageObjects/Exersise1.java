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
    private static final String HELLO_WORLD_INCORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Hello World!\")}}";
    private static final String NOT_HELLO_WORLD = "public class Main {public static void main (String [] args) {System.out.println(\"Telly Tubby!\");}}";
    private static final String NOT_MAIN_CLASS = "public class ain {public static void main (String [] args) {System.out.println(\"Hello World!\");}}";
    private static final String NOT_MAIN_METHOD = "public class Main {public static void ain (String [] args) {System.out.println(\"Hello World!\");}}";
    private static final String HELLO_WORLD_INFORMATION =
        "import java.util.List;\n" +
        "import java.util.ArrayList;\n" +
        "\n" +
        "public class Main {\n" +
        "    public static void main(String[] args) {\n" +
        "        List<?> l = new ArrayList<>();\n" +
        "        List<String> l2 = (List<String>) l;\n" +
        "        System.out.println(\"Hello World!\");\n" +
        "    }\n" +
        "}";
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

    public void enterNotHelloWorldToInput() {
        findAndType(INPUT_BOX, NOT_HELLO_WORLD);
    }

    public void enterNotMainClassToInput() {
        findAndType(INPUT_BOX, NOT_MAIN_CLASS);
    }

    public void enterHelloWorldInformation() {
        findAndType(INPUT_BOX, HELLO_WORLD_INFORMATION);
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
        return outputbox.getText();
    }

    public boolean informationBoxDisplayed() {
        waitForDivToBeVisible(INFORMATION_BOX);
        WebElement outputbox = driver.findElement(INFORMATION_BOX);
        return true;
    }

    public void enterNotMainMethodToInput() {
        findAndType(INPUT_BOX, NOT_MAIN_METHOD);
    }
}
