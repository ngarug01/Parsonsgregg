package com.ten10.training.javaparsons.webapp.views;

import com.ten10.training.javaparsons.ProgressReporter;

public class Results implements ProgressReporter {

    private String output;

    public String getOutput() {
        return output;
    }

    @Override
    public void storeCapturedOutput(String output) {
        this.output = output;
    }

}
