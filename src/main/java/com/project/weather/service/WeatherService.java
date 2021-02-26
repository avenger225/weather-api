package com.project.weather.service;

import com.project.weather.configuration.SurfingConditionsConfiguration;
import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.dto.DailyWeather;
import com.project.weather.webclient.dto.Forecast;
import com.project.weather.webclient.dto.Weather;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final SurfingConditionsConfiguration surfingConditionsConfiguration;
    private final SurfingLocationRepository surfingLocationRepository;

    public Forecast getWeather(final String city) {
        return weatherClient.getWeatherForCity(city);
    }

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll();
    }

    @Cacheable(value = "SurfingLocationForecast")
    public List<Forecast> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> getWeather(surfingLocation.getCity())).collect(Collectors.toList());
    }

    public Map<LocalDate, List<DailyWeather>> getDailyWeatherMap(final List<Forecast> forecastList) {
        final Map<LocalDate, List<DailyWeather>> dateDailyWeatherMap = new HashMap<>();
        for (Forecast forecast: forecastList) {
            for (Weather weather: forecast.getWeatherList()) {
                if (surfingConditionsConfiguration.weatherSuitableForSurfing(weather.getWindSpeed(), weather.getTemperature())) {
                    if (dateDailyWeatherMap.containsKey(weather.getDate())) {
                        dateDailyWeatherMap.
                                get(weather.getDate()).
                                add(new DailyWeather(forecast.getCityName(), weather.getDate(), weather.getWindSpeed(), weather.getTemperature()));
                    } else {
                        dateDailyWeatherMap.
                                computeIfAbsent(weather.getDate(), k -> new ArrayList<>()).
                                add(new DailyWeather(forecast.getCityName(), weather.getDate(), weather.getWindSpeed(), weather.getTemperature()));
                    }
                }
            }
        }
        return dateDailyWeatherMap;
    }
}

