package com.ten10.training.javaparsons.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaptureConsoleOutputTest {

    private CaptureConsoleOutput cco = new CaptureConsoleOutput();

    @Test
    void start() {
        cco.start();
        System.out.println("I am a telly tubby developer");
        assertEquals("I am a telly tubby developer\r\n", cco.stop());
    }
}
