package com.project.weather.controller;

import com.project.weather.model.SurfingLocation;
import com.project.weather.service.WeatherService;
import com.project.weather.webclient.dto.Forecast;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather/{city}")
    public Forecast getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }

    @GetMapping("/weather/surfing")
    public List<Forecast> getSurfingWeather() {
        return weatherService.getSurfingWeather();
    }

    @GetMapping("/surfing-locations")
    public List<SurfingLocation> getSurfingLocations() {
        return weatherService.getSurfingLocations();
    }
}
