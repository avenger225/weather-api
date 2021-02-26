package com.project.weather.service;

import com.project.weather.comparator.SurfingDailyWeatherComparator;
import com.project.weather.configuration.SurfingConditionsConfiguration;
import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.dto.DailyWeather;
import com.project.weather.webclient.dto.Forecast;
import com.project.weather.webclient.dto.Weather;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
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
                                add(new DailyWeather(forecast.getCityName(), weather.getWindSpeed(), weather.getTemperature()));
                    } else {
                        dateDailyWeatherMap.
                                computeIfAbsent(weather.getDate(), w -> new ArrayList<>()).
                                add(new DailyWeather(forecast.getCityName(), weather.getWindSpeed(), weather.getTemperature()));
                    }
                }
            }
        }
        return dateDailyWeatherMap;
    }

    public String getBestSurfingLocationForDate(final Map<LocalDate, List<DailyWeather>> dailyWeatherMap, final LocalDate date) {
        final List<DailyWeather> dailyWeatherList = dailyWeatherMap.get(date);
        if (CollectionUtils.isEmpty(dailyWeatherList)) {
            return null;
        }
        final Optional<DailyWeather> bestWeather = dailyWeatherList.stream().max(new SurfingDailyWeatherComparator());
        return bestWeather.map(DailyWeather::getCity).orElse(null);
    }
}

