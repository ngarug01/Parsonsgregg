package com.ten10.training.javaparsons.webapp.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExercisesControllerTest {

    private static final ExerciseController.ExerciseInformation exerciseInformation = new ExerciseController.ExerciseInformation("URL", "Title", "Description", "code", "code", 1);

    @Test
    void exerciseInformationGetURL () {
        assertEquals("URL", exerciseInformation.getUrl());
    }

    @Test
    void exerciseInformationGetTitle () {
        assertEquals("Title",exerciseInformation.getTitle());
    }

    @Test
    void exerciseInformationGetDescription () {
        assertEquals("Description", exerciseInformation.getDescription());
    }

    @Test
    void exerciseInformationGetPrecedingCode () {
        assertEquals("code", exerciseInformation.getPrecedingCode());
    }

    @Test
    void exerciseInformationGetFollowingCode () {
        assertEquals("code", exerciseInformation.getFollowingCode());
    }
}
