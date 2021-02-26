package com.project.weather.webclient;

import com.project.weather.webclient.dto.Forecast;
import com.project.weather.configuration.WeatherApiConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class WeatherClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final WeatherApiConfiguration weatherApiConfiguration;

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
