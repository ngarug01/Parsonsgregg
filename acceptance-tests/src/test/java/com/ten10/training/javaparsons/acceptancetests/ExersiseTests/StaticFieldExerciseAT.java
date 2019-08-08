package com.ten10.training.javaparsons.acceptancetests.ExersiseTests;

import com.ten10.training.javaparsons.acceptancetests.ExersisePageObjects.StaticFieldExercises;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
public class StaticFieldExerciseAT {

    private final WebDriver driver;
    private final StaticFieldExercises staticFieldExercises;
    private String result;

    public StaticFieldExerciseAT(ChromeDriver driver) {
        this.driver = driver;
        staticFieldExercises = new StaticFieldExercises(this.driver);
    }


    @BeforeEach
    void goToHomepage() {
        staticFieldExercises.goToHomepage();
    }

    @Test
    @Tag("acceptance-tests")
    void containsCorrectAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.correctAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromCorrectAnswerBox();
        assertTrue(result.contains("Correct answer. Well done!"));
    }

    @Test
    @Tag("acceptance-tests")
    void doesNotContainCorrectAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.incorrectAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromIncorrectAnswerBox();
        assertTrue(result.contains("Incorrect answer"));
    }

    @Test
    @Tag("acceptance-tests")
    void incorrectTypeAnswer() {
        staticFieldExercises.selectStaticFieldExercise();
        staticFieldExercises.incorrectTypeStaticFieldAnswer();
        staticFieldExercises.submitAnswer();
        result = staticFieldExercises.readFromIncorrectAnswerBox();
        assertFalse(result.contains("int"));
        assertTrue(result.contains("Incorrect answer"));
    }
}
