package com.project.weather.controller;

import com.project.weather.model.SurfingLocation;
import com.project.weather.service.WeatherCacheService;
import com.project.weather.service.WeatherService;
import com.project.weather.webclient.dto.DailyWeather;
import com.project.weather.webclient.dto.Forecast;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherCacheService weatherCacheService;

    @GetMapping("/weather/{city}")
    public Forecast getWeather(@PathVariable String city) {
        return weatherCacheService.getWeather(city);
    }

    @GetMapping("/weather/surfing")
    public List<Forecast> getSurfingWeather() {
        return weatherCacheService.getSurfingWeather();
    }

    @GetMapping("/weather/map")
    public Map<LocalDate, List<DailyWeather>> getDailyWeatherMap() {
        return weatherCacheService.getDailyWeatherMap();
    }

    @GetMapping("/surfing-locations")
    public List<SurfingLocation> getSurfingLocations() {
        return weatherCacheService.getSurfingLocations();
    }

    @GetMapping("/best-surfing-location/{date}")
    public String getBestSurfingLocationForDate(@PathVariable String date){
        return weatherService.getBestSurfingLocationForDate(LocalDate.parse(date));
    }
}
