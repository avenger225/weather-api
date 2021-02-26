package com.project.weather.service;

import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.dto.ForecastDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public ForecastDto getWeather() {
        return weatherClient.getWeatherForCity("Le Morne");
    }


}
