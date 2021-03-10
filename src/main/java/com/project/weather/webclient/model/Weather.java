package com.project.weather.webclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
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
