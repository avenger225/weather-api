package com.project.weather.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import com.project.weather.webclient.model.Weather;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ClassRule
    public static WireMockClassRule classRule = new WireMockClassRule(options().
            port(80).httpsPort(443));

    @Rule
    public WireMockClassRule rule = classRule;

    @Autowired
    private ObjectMapper objectMapper;

    private String getJsonFromResource(String filename) throws IOException {
        File file = new ClassPathResource("/json/" + filename).getFile();
        Forecast forecast = objectMapper.readValue(file, Forecast.class);
        LocalDate today = LocalDate.now();
        List<Weather> weatherList = forecast.getWeatherList();
        for (int i = 0; i < weatherList.size(); i++) {
            Weather weather = weatherList.get(i);
            weather.setDate(today.plusDays(i));
        }
        return objectMapper.writeValueAsString(forecast);
    }

    @Before
    public void setMockData() throws IOException {
        givenThat(WireMock.get(urlEqualTo("/weather?city=Le%20Gros-Morne&key=123"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("morne.json"))));

        givenThat(WireMock.get(urlEqualTo("/weather?city=Bridgetown&key=123"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("bridgetown.json"))));


        givenThat(WireMock.get(urlEqualTo("/weather?city=Fortaleza&key=123"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("fortaleza.json"))));


        givenThat(WireMock.get(urlEqualTo("/weather?city=Jastarnia&key=123"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("jastarnia.json"))));


        givenThat(WireMock.get(urlEqualTo("/weather?city=Pissouri&key=123"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("pissouri.json"))));
    }

    @Test
    public void shouldGetForecastForGivenCity() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/{city}", "Le Gros-Morne"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Forecast forecastList = objectMapper.readValue(result.getResponse().getContentAsString(), Forecast.class);
        assertThat(forecastList.getCityName()).isEqualTo("Le Gros-Morne");
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
        mockMvc.perform(get("/weather/best-surfing-location/{date}", LocalDate.now().minusDays(3).format(DateTimeFormatter.ISO_DATE)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(""));
    }

    @Test
    public void shouldGetSurfingLocations() throws Exception {
        MvcResult result = mockMvc.perform(get("/weather/surfing"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        List<Forecast> forecastList = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Forecast[].class));
        assertThat(forecastList).isNotNull();
    }
}
