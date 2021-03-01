package com.project.weather.webclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyWeather {
    private String city;
    private double windSpeed;
    private double temperature;
    private double bestLocationValue;

    public DailyWeather(String city, double windSpeed, double temperature) {
        this.city = city;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.bestLocationValue = (windSpeed / 3) + temperature;
    }
}
