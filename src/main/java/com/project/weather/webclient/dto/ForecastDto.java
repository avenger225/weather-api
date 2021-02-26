package com.project.weather.webclient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@NoArgsConstructor
public class ForecastDto {
    private String cityName;
    private double longitude;
    private double latitude;
    private List<WeatherDto> weatherList;

    @JsonCreator
    public ForecastDto(@JsonProperty("city_name") String cityName, @JsonProperty("lon") double longitude,
                       @JsonProperty("lat") double latitude, @JsonProperty("data") List<WeatherDto> weatherList) {
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weatherList = weatherList;
    }
}
