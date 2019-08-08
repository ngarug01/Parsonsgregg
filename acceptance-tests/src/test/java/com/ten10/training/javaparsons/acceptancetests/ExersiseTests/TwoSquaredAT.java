package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.TwoSquared;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
public class TwoSquaredAT {
    private final WebDriver driver;
    private final TwoSquared twoSquared;
    private String result;

    public TwoSquaredAT(ChromeDriver driver) {
        this.driver = driver;
        twoSquared = new TwoSquared(this.driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        twoSquared.goToHomepage();
    }

    @Test
    @Tag("acceptance-tests")
    void twoSquaredInputted() {
        twoSquared.chooseExercise4();
        twoSquared.enterTwoSquaredToInput();
        twoSquared.clickEnterAnswer();
        result = twoSquared.readFromCorrectOutputBox();
        assertTrue(result.contains("Correct answer"));
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        twoSquared.chooseExercise4();
        assertTrue(twoSquared.readFromDescription().contains("Two Squared"));
    }

}
