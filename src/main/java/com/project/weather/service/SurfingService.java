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

    public List<SurfingLocation> getSurfingLocations() {
        return surfingLocationRepository.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Forecast> getSurfingWeather() {
        return getSurfingLocations().stream().map(surfingLocation -> weatherClient.getWeatherForCity(surfingLocation.getCity()))
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean weatherSuitableForSurfing(final double windSpeed, final double temperature) {
        return (isWindNotTooWeak(windSpeed) && isWindNotTooStrong(windSpeed)) && (isTemperatureNotTooLow(temperature) && isTemperatureNotTooHigh(temperature));
    }

    protected boolean isWindNotTooWeak(final double windSpeed) {
        return windSpeed >= surfingConditionsConfiguration.getWindSpeedMin();
    }

    protected boolean isWindNotTooStrong(final double windSpeed) {
        return windSpeed <= surfingConditionsConfiguration.getWindSpeedMax();
    }

    protected boolean isTemperatureNotTooLow(final double temperature) {
        return temperature >= surfingConditionsConfiguration.getTempMin();
    }

    protected boolean isTemperatureNotTooHigh(final double temperature) {
        return temperature <= surfingConditionsConfiguration.getTempMax();
    }

}
