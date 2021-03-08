package com.project.weather.service;

import com.project.weather.configuration.SurfingConditionsConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SurfingService {

    private final SurfingConditionsConfiguration surfingConditionsConfiguration;

    public boolean weatherSuitableForSurfing(final double windSpeed, final double temperature) {
        return (windSpeed >= surfingConditionsConfiguration.getWindSpeedMin() && windSpeed <= surfingConditionsConfiguration.getWindSpeedMax())
                && (temperature >= surfingConditionsConfiguration.getTempMin() && temperature <= surfingConditionsConfiguration.getTempMax());
    }
}
