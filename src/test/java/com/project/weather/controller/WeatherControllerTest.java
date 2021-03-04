package com.project.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.weather.webclient.dto.Forecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getSurfingLocations() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/surfing"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        List<Forecast> forecastList = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Forecast[].class));
        assertThat(forecastList).isNotNull();
    }

    @Test
    public void getForecastForGivenCity() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/{city}", "Szczecin"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Forecast forecastList = objectMapper.readValue(result.getResponse().getContentAsString(), Forecast.class);
        assertThat(forecastList.getCityName()).isEqualTo("Szczecin");
    }
}
