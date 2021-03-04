package com.project.weather.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.weather.model.SurfingLocation;
import com.project.weather.service.WeatherCacheService;
import com.project.weather.service.WeatherService;
import com.project.weather.webclient.dto.DailyWeather;
import com.project.weather.webclient.dto.Forecast;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherCacheService weatherCacheService;

    @GetMapping("/weather/{city}")
    public ResponseEntity<Forecast> getWeather(@PathVariable String city) {
        return new ResponseEntity<>(weatherCacheService.getWeather(city), HttpStatus.OK);
    }

    @GetMapping("/weather/surfing")
    public ResponseEntity<List<Forecast>> getSurfingWeather() {
        return new ResponseEntity<>(weatherCacheService.getSurfingWeather(), HttpStatus.OK);
    }

    @GetMapping("/weather/map")
    public ResponseEntity<Map<LocalDate, List<DailyWeather>>> getDailyWeatherMap() {
        return new ResponseEntity<>(weatherCacheService.getDailyWeatherMap(), HttpStatus.OK);
    }

    @GetMapping("/surfing-locations")
    public ResponseEntity<List<SurfingLocation>> getSurfingLocations() {
        return new ResponseEntity<>(weatherCacheService.getSurfingLocations(), HttpStatus.OK);
    }

    @GetMapping("/best-surfing-location/{date}")
    public ResponseEntity<String> getBestSurfingLocationForDate(@PathVariable String date){
        return new ResponseEntity<>(weatherService.getBestSurfingLocationForDate(LocalDate.parse(date)), HttpStatus.OK);
    }

    @ControllerAdvice
    public class ExceptionHelper {
        @ExceptionHandler(value = {HttpClientErrorException.class})
        public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException exception) {
            return new ResponseEntity<>(exception.getMessage() + exception.getStatusText(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(value = {HttpServerErrorException.class})
        public ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException exception) {
            return new ResponseEntity<>(exception.getMessage() + exception.getStatusText(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(value = {JWTVerificationException.class})
        public ResponseEntity<Object> handleJWTVerificationException(JWTVerificationException exception) {
            return new ResponseEntity<>("TOKEN ERROR: " + exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
