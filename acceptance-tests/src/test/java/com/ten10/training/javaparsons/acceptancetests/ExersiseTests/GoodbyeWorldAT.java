package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
public class GoodbyeWorldAT {
    private static final String GOODBYE_WORLD_CORRECT = "public class Main {public static void main(String[] args) {System.out.println(\"Goodbye Cruel World!\");}}";


    private final WebDriver driver;
    private final ExercisePage page;

    public GoodbyeWorldAT(ChromeDriver driver) {
        this.driver = driver;
        this.page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExcercise(2, "Goodbye Cruel World!");
    }

    @Test
    @Tag("acceptance-tests")
    void goodbyeCruelWorldInputted() {
        page.trySolution(GOODBYE_WORLD_CORRECT);
        assertThat(page.getOutput(), is("Goodbye Cruel World!"));
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void descriptionChanges() {
        assertThat(page.getDescription(), containsString("Goodbye Cruel World!"));
    }
}
