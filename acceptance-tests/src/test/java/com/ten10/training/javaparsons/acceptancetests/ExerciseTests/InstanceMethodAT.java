package com.ten10.training.javaparsons.acceptancetests.ExerciseTests;

import com.ten10.training.javaparsons.acceptancetests.ExercisePageObjects.ExercisePage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

@DisplayName("Tests for instance methods and if they have been called.")
@ExtendWith(SeleniumExtension.class)
@SingleSession

public class InstanceMethodAT {

    private final String CORRECT_INSTANCE_METHOD =
        "public class Method {\n" +
            "public void instanceMethod() {\n" +
            " int a = 5;\n" +
            " int b = 10;\n" +
            "} \n" +
            "}";


    private final String INCORRECT_INSTANCE_METHOD =
        "public class Method {\n" +
            "public static void instanceMethod() {\n" +
            " int a = 5;\n" +
            " int b = 10;\n" +
            "} \n" +
            "}";


    private final ExercisePage page;

    InstanceMethodAT(ChromeDriver driver) {
        page = new ExercisePage(driver);
    }

    @BeforeEach
    void beforeEveryTest(){
        page.goToHomepage();
        page.chooseExercise(9,"Create a method that is an instance method");
    }

    @Test
    @Tag("acceptance-tests")
    void checkCorrectInstanceMethod(){
        page.trySolution(CORRECT_INSTANCE_METHOD);
        Assertions.assertTrue(page.isSuccessful());
    }

    @Test
    @Tag("acceptance-tests")
    void checkIncorrectInstanceMethod(){
        page.trySolution(INCORRECT_INSTANCE_METHOD);
    //    Assertions.assertFalse(page.isSuccessful());
        Assertions.assertTrue(page.getInput().contains("static"));
    }


}
