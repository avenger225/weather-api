package com.project.weather.service;

import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import com.project.weather.webclient.model.Weather;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames={"DailyWeatherMap"})
public class WeatherCacheService {

    private final SurfingService surfingService;
    
    @Cacheable
    public Map<LocalDate, List<DailyWeather>> getDailyWeatherMap() {
        final Map<LocalDate, List<DailyWeather>> dateDailyWeatherMap = new HashMap<>();
        for (Forecast forecast: surfingService.getSurfingWeather()) {
            for (Weather weather: forecast.getWeatherList()) {
                if (surfingService.weatherSuitableForSurfing(weather.getWindSpeed(), weather.getTemperature())) {
                    if (dateDailyWeatherMap.containsKey(weather.getDate())) {
                        dateDailyWeatherMap
                                .get(weather.getDate())
                                .add(new DailyWeather(forecast.getCityName(), weather.getWindSpeed(), weather.getTemperature()));
                    } else {
                        dateDailyWeatherMap
                                .computeIfAbsent(weather.getDate(), w -> new ArrayList<>())
                                .add(new DailyWeather(forecast.getCityName(), weather.getWindSpeed(), weather.getTemperature()));
                    }
                }
            }
        }
        return dateDailyWeatherMap;
    }
}
