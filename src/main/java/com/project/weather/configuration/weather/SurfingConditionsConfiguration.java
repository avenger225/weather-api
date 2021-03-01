package com.project.weather.configuration.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SurfingConditionsConfiguration {

    @Value("${surfing.conditions.temp.min}")
    private double tempMin;

    @Value("${surfing.conditions.temp.max}")
    private double tempMax;

    @Value("${surfing.conditions.windSpeed.min}")
    private double windSpeedMin;

    @Value("${surfing.conditions.windSpeed.max}")
    private double windSpeedMax;

    public boolean weatherSuitableForSurfing(final double windSpeed, final double temperature) {
        return (windSpeed >= windSpeedMin && windSpeed <= windSpeedMax) && (temperature >= tempMin && temperature <= tempMax);
    }
}
