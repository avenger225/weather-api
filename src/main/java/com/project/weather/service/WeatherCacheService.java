package com.project.weather.service;

import com.project.weather.configuration.weather.SurfingConditionsConfiguration;
import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import com.project.weather.webclient.model.Weather;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeatherCacheService {

    private final SurfingService surfingService;
    private final SurfingLocationRepository surfingLocationRepository;
    private final WeatherClient weatherClient;

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    public Forecast getWeather(final String city) {
        return weatherClient.getWeatherForCity(city);
    }

    public List<Forecast> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> getWeather(surfingLocation.getCity())).collect(Collectors.toUnmodifiableList());
    }

    @Cacheable(value = "DailyWeatherMap")
    public Map<LocalDate, List<DailyWeather>> getDailyWeatherMap() {
        final Map<LocalDate, List<DailyWeather>> dateDailyWeatherMap = new HashMap<>();
        for (Forecast forecast: getSurfingWeather()) {
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
