package com.project.weather.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoginAndGetContent() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"login\": \"test\", \"password\": \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/login/test")
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("test"));

    }

    @Test
    void loginShouldFailedWithUnknownCredentials () throws Exception {
        mockMvc.perform(post("/login")
                .content("{\"login\": \"test123\", \"password\": \"test123\"}"))
                .andDo(print())
                .andExpect(status().is(401));
    }
}
