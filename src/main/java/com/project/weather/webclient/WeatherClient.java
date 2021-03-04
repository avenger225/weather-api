package com.project.weather.webclient;

import com.project.weather.configuration.RestTemplateResponseErrorHandler;
import com.project.weather.configuration.weather.WeatherApiConfiguration;
import com.project.weather.webclient.dto.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {
    private final RestTemplate restTemplate;

    @Autowired
    private WeatherApiConfiguration weatherApiConfiguration;

    @Autowired
    public WeatherClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Forecast getWeatherForCity(String city) {
        return callGetMethod(weatherApiConfiguration.getWeatherApiForecastCity(),
                Forecast.class,
                city, weatherApiConfiguration.getWeatherApiKey());
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(weatherApiConfiguration.getWeatherApiUrl() + url,
                responseType, objects);
    }
}
