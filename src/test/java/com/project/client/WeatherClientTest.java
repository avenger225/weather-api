package com.project.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.project.weather.webclient.model.Forecast;
import com.project.weather.webclient.model.Weather;
import org.apache.http.conn.HttpClientConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@WithMockUser
public class WeatherClientTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private HttpClientConnectionManager restConnectionManager;
//
//    @After
//    @Before
//    public void resetRestHttpConnections() {
//        restConnectionManager.closeExpiredConnections();
//        restConnectionManager.closeIdleConnections(0, TimeUnit.SECONDS);
//    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(1840);

//    @After
//    public void resetRestMock() {REST.resetAll();}

//    @Before
//    public void setupWeatherApi() throws IOException {
//        wireMockRule.stubFor(WireMock.get("http://localhost:1840/weather-api?city={Pissouri}&key={123456}")
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(getJsonFromResource("pissouri.json")))
//        );
//    }



    @Test
    public void shouldGetForecastForGivenCity() throws Exception {
        WireMock.configureFor("localhost", 1840);
        wireMockRule.stubFor(WireMock.get("http://localhost:1840/weather-api?city={Pissouri}&key={123456}")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getJsonFromResource("pissouri.json")))
        );

        MvcResult result = mockMvc.perform(get("/weather/{city}", "Pissouri"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Forecast forecastList = objectMapper.readValue(result.getResponse().getContentAsString(), Forecast.class);
        assertThat(forecastList.getCityName()).isEqualTo("Pissouri");
    }

    public String getJsonFromResource(String filename) throws IOException {
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
}
