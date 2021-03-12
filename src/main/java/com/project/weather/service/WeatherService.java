package com.project.weather.service;

import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherCacheService weatherCacheService;
    private final WeatherClient weatherClient;

    public Forecast getWeather(final String city) {
        return weatherClient.getWeatherForCity(city);
    }

    public Map<LocalDate, List<DailyWeather>> getDailyWeather() {
        return weatherCacheService.getDailyWeatherMap();
    }

    public DailyWeather getBestSurfingLocationForDate(final LocalDate date) {
        final List<DailyWeather> dailyWeatherList = weatherCacheService.getDailyWeatherMap().get(date);
        if (CollectionUtils.isEmpty(dailyWeatherList)) {
            return null;
        }
        final Optional<DailyWeather> bestWeather = dailyWeatherList.stream().max(Comparator.comparing(DailyWeather::getSurferCoefficient));
        return bestWeather.orElse(null);
    }
}

