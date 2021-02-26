package com.project.weather.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyWeather {
    private String city;
    private LocalDate date;
    private double windSpeed;
    private double temperature;
}
