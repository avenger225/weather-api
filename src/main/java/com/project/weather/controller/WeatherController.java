package com.project.weather.controller;

import com.project.weather.webclient.dto.ForecastDto;
import com.project.weather.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    @ResponseBody
    public ForecastDto getWeather() {
        return weatherService.getWeather();
    }
}
