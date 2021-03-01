package com.project.weather.webclient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Forecast {
    private String cityName;
    private double longitude;
    private double latitude;
    private List<Weather> weatherList;

    @JsonCreator
    public Forecast(@JsonProperty("city_name") String cityName, @JsonProperty("lon") double longitude,
                    @JsonProperty("lat") double latitude, @JsonProperty("data") List<Weather> weatherList) {
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weatherList = weatherList;
    }
}
