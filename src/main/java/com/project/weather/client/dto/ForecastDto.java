package com.project.weather.client.dto;

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
    private double lon;
    private double lat;
    private List<WeatherDto> weatherList;

    @JsonCreator
    public ForecastDto(@JsonProperty("city_name") String cityName, @JsonProperty("lon") double lon,
                   @JsonProperty("lat") double lat, @JsonProperty("data") List<WeatherDto> weatherList) {
        this.cityName = cityName;
        this.lon = lon;
        this.lat = lat;
        this.weatherList = weatherList;
    }
}
