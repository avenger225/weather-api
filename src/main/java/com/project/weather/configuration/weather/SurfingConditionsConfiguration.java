package com.project.weather.configuration.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SurfingConditionsConfiguration {

    private final double tempMin;
    private final double tempMax;
    private final double windSpeedMin;
    private final double windSpeedMax;

    public SurfingConditionsConfiguration(@Value("${surfing.conditions.temp.min}") double tempMin,
                                          @Value("${surfing.conditions.temp.max}") double tempMax,
                                          @Value("${surfing.conditions.windSpeed.min}") double windSpeedMin,
                                          @Value("${surfing.conditions.windSpeed.max}") double windSpeedMax) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.windSpeedMin = windSpeedMin;
        this.windSpeedMax = windSpeedMax;
    }

    public boolean weatherSuitableForSurfing(final double windSpeed, final double temperature) {
        return (windSpeed >= windSpeedMin && windSpeed <= windSpeedMax) && (temperature >= tempMin && temperature <= tempMax);
    }
}
