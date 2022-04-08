package com.ten10.training.javaparsons.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.webapp.WebApp;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = WebApp.class)
@AutoConfigureMockMvc
class ExerciseControllerTest {
    // TODO: Fix
    private static final String TRIVIAL_INPUT = "{\"input\": \"foo\"}";
    private static final String TRIVIAL_OUTPUT = "{}";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    ExerciseRepository exerciseRepository;

    @BeforeEach
    void setup() throws Exception {
        Exercise exercise1 = mock(Exercise.class);
        when(exerciseRepository.getExerciseByIdentifier(1)).thenReturn(exercise1);
        when(exercise1.getSolutionFromUserInput(anyString(), any(ProgressReporter.class))).thenAnswer((Answer<Solution>) invocationOnMock -> {
            ProgressReporter progressReporter = invocationOnMock.getArgument(1);
            return () -> {
                progressReporter.storeCapturedOutput(TRIVIAL_OUTPUT);
                return true;
            };
        });

    }

    @Test
    void trivialInputShouldLoad() throws IOException {
        SubmittedSolution value = objectMapper.readValue(TRIVIAL_INPUT, SubmittedSolution.class);
        assertThat(value, is(notNullValue()));
        assertThat(value.getInput(), is("foo"));
    }

    @Test
    void serialiseResult() throws JsonProcessingException {
        Results results = new Results();
        results.storeCapturedOutput("Foo");
        String output = objectMapper.writeValueAsString(results);
        assertThat(output, is("{\"output\":\"Foo\",\"successfulSolution\":false,\"compilerErrors\":[],\"compilerInfo\":[],\"runnerErrors\":[]}"));
    }

    @Test
    void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk());
    }

    @Test
    void submitExerciseSuccessfully() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/exercise/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TRIVIAL_INPUT);
        mvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().json(TRIVIAL_OUTPUT));

    }

    @Test
    void serialiseCompilerError() throws JsonProcessingException {
        Results results = new Results();
        results.storeCapturedOutput("Null");
        results.reportCompilerError(3, "incorrect Method");
        String output = objectMapper.writeValueAsString(results);
        assertThat(output, is("{\"output\":\"Null\",\"successfulSolution\":false,\"compilerErrors\":[{\"lineNumber\":3,\"message\":\"incorrect Method\"}],\"compilerInfo\":[],\"runnerErrors\":[]}"));
    }


    @Test
    void getDropdownListMembersReturnsListInputText() {
        List<String> dropdownListMembers = new ExerciseController().getDropdownListMembers();
        assertTrue(dropdownListMembers.contains("") &&
            dropdownListMembers.contains("public class Main {") &&
            dropdownListMembers.contains("public static void main(String[] args) {") &&
            dropdownListMembers.contains("System.out.println(\"Exercise Paths!\");") &&
            dropdownListMembers.contains("}"));
    }
}
