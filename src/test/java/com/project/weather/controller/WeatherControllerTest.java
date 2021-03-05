package com.project.weather.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    public void shouldGetSurfingLocations() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/surfing"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        List<Forecast> forecastList = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Forecast[].class));
        assertThat(forecastList).isNotNull();
    }

    @Test
    public void shouldGetForecastForGivenCity() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/{city}", "Szczecin"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Forecast forecastList = objectMapper.readValue(result.getResponse().getContentAsString(), Forecast.class);
        assertThat(forecastList.getCityName()).isEqualTo("Szczecin");
    }

    @Test
    public void shouldGetForecastForEmbeddedCities() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/map"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        TypeReference<HashMap<LocalDate, List<DailyWeather>>> typeRef
                = new TypeReference<>() {
        };
        Map<LocalDate, List<DailyWeather>> map = objectMapper.readValue(result.getResponse().getContentAsString(), typeRef);
        assertThat(map).isNotEmpty();
    }

    @Test
    public void shouldGetForecastForTomorrow() throws Exception {
        mockMvc.perform(get("/weather/best-surfing-location/{date}", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE)))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void shouldGetEmptyForecastForYesterday() throws Exception {
        mockMvc.perform(get("/weather/best-surfing-location/{date}", LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(""));
    }
}
