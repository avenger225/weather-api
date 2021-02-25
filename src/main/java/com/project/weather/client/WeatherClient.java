package com.project.weather.client;

import com.project.weather.client.dto.ForecastDto;
import com.project.weather.configuration.WeatherApiConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class WeatherClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final WeatherApiConfiguration weatherApiConfiguration;

    public ForecastDto getWeatherForCity(String city) {
        System.out.println("test: " + weatherApiConfiguration.getWeatherApiKey());
        return callGetMethod("/forecast/daily?city={city}&key={API_KEY}",
                ForecastDto.class,
                city, weatherApiConfiguration.getWeatherApiKey());
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(weatherApiConfiguration.getWeatherApiUrl() + url,
                responseType, objects);
    }
}
