package com.project.weather.service;

import com.project.weather.comparator.SurfingDailyWeatherComparator;
import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherCacheService weatherCacheService;
    private final SurfingLocationRepository surfingLocationRepository;
    private final WeatherClient weatherClient;

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Forecast> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> getWeather(surfingLocation.getCity())).collect(Collectors.toUnmodifiableList());
    }

    public Forecast getWeather(final String city) {
        return weatherClient.getWeatherForCity(city);
    }

    public String getBestSurfingLocationForDate(final LocalDate date) {
        final List<DailyWeather> dailyWeatherList = weatherCacheService.getDailyWeatherMap(getSurfingWeather()).get(date);
        if (CollectionUtils.isEmpty(dailyWeatherList)) {
            return null;
        }
        final Optional<DailyWeather> bestWeather = dailyWeatherList.stream().max(new SurfingDailyWeatherComparator());
        return bestWeather.map(DailyWeather::getCity).orElse(null);
    }
}

