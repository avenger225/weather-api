package com.project.weather.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class WeatherApiConfiguration {

    @Value("${weatherbit.api.url}")
    private String weatherApiUrl;

    @Value("${weatherbit.api.key}")
    private String weatherApiKey;

    @Value("${weatherbit.api.city}")
    private String weatherApiForecastCity;
}
