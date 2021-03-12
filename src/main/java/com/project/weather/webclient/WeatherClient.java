package com.project.weather.webclient;

import com.project.weather.configuration.handler.RestTemplateResponseErrorHandler;
import com.project.weather.configuration.WeatherApiConfiguration;
import com.project.weather.webclient.model.Forecast;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {
    private final RestTemplate restTemplate;
    private final WeatherApiConfiguration weatherApiConfiguration;

    public WeatherClient(RestTemplateBuilder restTemplateBuilder, WeatherApiConfiguration weatherApiConfiguration) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.weatherApiConfiguration = weatherApiConfiguration;
    }

    public Forecast getWeatherForCity(final String city) {
        return callGetMethod(weatherApiConfiguration.getWeatherApiForecastCity(),
                Forecast.class,
                city, weatherApiConfiguration.getWeatherApiKey());
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(weatherApiConfiguration.getWeatherApiUrl() + url,
                responseType, objects);
    }
}
