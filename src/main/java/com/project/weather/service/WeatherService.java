package com.project.weather.service;

import com.project.weather.comparator.SurfingDailyWeatherComparator;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherCacheService weatherCacheService;

    public List<Forecast> getSurfingWeather() {
        return weatherCacheService.getSurfingWeather();
    }

    public Map<LocalDate, List<DailyWeather>> getDailyWeather() {
        return weatherCacheService.getDailyWeatherMap();
    }

    public Forecast getWeather(final String city) {
        return weatherCacheService.getWeather(city);
    }

    public String getBestSurfingLocationForDate(final LocalDate date) {
        final List<DailyWeather> dailyWeatherList = weatherCacheService.getDailyWeatherMap().get(date);
        if (CollectionUtils.isEmpty(dailyWeatherList)) {
            return null;
        }
        final Optional<DailyWeather> bestWeather = dailyWeatherList.stream().max(new SurfingDailyWeatherComparator());
        return bestWeather.map(DailyWeather::getCity).orElse(null);
    }
}

