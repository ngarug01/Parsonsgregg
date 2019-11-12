package com.ten10.training.javaparsons.webapp.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DropdownListMembersTest {

    @Test
    void classOpenInputTextTest () {
        assertEquals("public class Main {", DropdownListMembers.classOpen.inputText);
    }

    @Test
    void mainMethodOpenInputTextTest () {
        assertEquals("public static void main(String[] args) {", DropdownListMembers.mainMethodOpen.inputText);
    }

    @Test
    void closeBracketInputTextTest () {
        assertEquals("}", DropdownListMembers.closeBracket.inputText);
    }


}
