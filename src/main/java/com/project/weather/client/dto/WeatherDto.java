package com.project.weather.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@NoArgsConstructor
public class WeatherDto {
    private double windSpeed;
    private LocalDate date;
    private double temp;

    @JsonCreator
    public WeatherDto(@JsonProperty("wind_spd") double windSpeed, @JsonProperty("valid_date") LocalDate date,
                      @JsonProperty("temp") double temp) {
        this.windSpeed = windSpeed;
        this.date = date;
        this.temp = temp;
    }
}
