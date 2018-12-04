package com.ten10.training.javaparsons.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    private static final String TRIVIAL_INPUT = "{\"input\": \"foo\"}";
    public static final String TRIVIAL_OUTPUT = "{}";


    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    private MockMvc mvc;

    @Test
    public void trivialInputShouldLoad() throws IOException {
        SubmittedSolution value = objectMapper.readValue(TRIVIAL_INPUT, SubmittedSolution.class);

        assertThat(value, is(notNullValue()));
        assertThat(value.getInput(), is("foo"));
    }

    @Test
    void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }

    @Test
    void submitExerciseSuccessfully() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/exercise1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TRIVIAL_INPUT);

        mvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().json(TRIVIAL_OUTPUT));
    }
}
