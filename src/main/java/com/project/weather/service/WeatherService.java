package com.project.weather.service;

import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.dto.ForecastDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final SurfingLocationRepository surfingLocationRepository;

    public ForecastDto getWeather(final String city) {
        return weatherClient.getWeatherForCity(city);
    }

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll();
    }

    public List<ForecastDto> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> getWeather(surfingLocation.getCity())).collect(Collectors.toList());
    }
}

