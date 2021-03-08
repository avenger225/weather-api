package com.project.weather.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class WeatherApiConfiguration {

    private final String weatherApiUrl;
    private final String weatherApiKey;
    private final String weatherApiForecastCity;

    public WeatherApiConfiguration(@Value("${weatherbit.api.url}") String weatherApiUrl,
                                   @Value("${weatherbit.api.key}") String weatherApiKey,
                                   @Value("${weatherbit.api.city}") String weatherApiForecastCity) {
        this.weatherApiUrl = weatherApiUrl;
        this.weatherApiKey = weatherApiKey;
        this.weatherApiForecastCity = weatherApiForecastCity;
    }
}
