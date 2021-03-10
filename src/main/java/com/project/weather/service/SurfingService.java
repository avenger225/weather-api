package com.project.weather.service;

import com.project.weather.configuration.SurfingConditionsConfiguration;
import com.project.weather.model.SurfingLocation;
import com.project.weather.repository.SurfingLocationRepository;
import com.project.weather.webclient.WeatherClient;
import com.project.weather.webclient.model.Forecast;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurfingService {

    private final SurfingConditionsConfiguration surfingConditionsConfiguration;
    private final SurfingLocationRepository surfingLocationRepository;
    private final WeatherClient weatherClient;

    public boolean weatherSuitableForSurfing(final double windSpeed, final double temperature) {
        return (windSpeed >= surfingConditionsConfiguration.getWindSpeedMin() && windSpeed <= surfingConditionsConfiguration.getWindSpeedMax())
                && (temperature >= surfingConditionsConfiguration.getTempMin() && temperature <= surfingConditionsConfiguration.getTempMax());
    }

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Forecast> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> weatherClient.getWeatherForCity(surfingLocation.getCity()))
                .collect(Collectors.toUnmodifiableList());
    }
}
