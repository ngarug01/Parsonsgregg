package com.ten10.training.javaparsons.acceptancetests.ExerciseTests;


import com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumExtension.class)
@SingleSession
@DisplayName("Tests for feature 5: Return Char A")
public class ReturnCharAAT {


    private final String INCORRECT_PROGRAM =
        "   char[] x = {'A','B'};\n" +
            "   return (x[1]);";
    private final String CORRECT_PROGRAM_THAT_PRINTS_CHAR_A =
        "   char[] x = {'A'};\n" +
            "   return (x[0]);";

    private final ExercisePage page;

    ReturnCharAAT(ChromeDriver driver) {
        page = new ExercisePage(driver);
    }


    @BeforeEach
    void beforeEveryTest() {
        page.goToHomepage();
        page.chooseExercise(5, "Return Char A");
    }

    @Test
    @Tag("acceptance-tests")
    void charAInputted() {
        page.trySolution(CORRECT_PROGRAM_THAT_PRINTS_CHAR_A);
        assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void notCharA() {
        page.trySolution(INCORRECT_PROGRAM);
        assertFalse(page.isSuccessful());
    }

}
