package com.project.weather.webclient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private double windSpeed;
    private LocalDate date;
    private double temperature;

    @JsonCreator
    public Weather(@JsonProperty("wind_spd") double windSpeed, @JsonProperty("valid_date") LocalDate date,
                   @JsonProperty("temp") double temperature) {
        this.windSpeed = windSpeed;
        this.date = date;
        this.temperature = temperature;
    }
}
