package com.project.weather.controller;

import com.project.weather.configuration.handler.ApiError;
import com.project.weather.service.SurfingService;
import com.project.weather.service.WeatherService;
import com.project.weather.webclient.model.DailyWeather;
import com.project.weather.webclient.model.Forecast;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("weather")
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final SurfingService surfingService;

    @ApiOperation(value = "Getting basic weather's forecast data for given city.")
    @GetMapping("/{city}")
    public ResponseEntity<Forecast> getWeather(@PathVariable String city) {
        return new ResponseEntity<>(weatherService.getWeather(city), HttpStatus.OK);
    }

    @ApiOperation(value = "Getting full weather's forecast list for cities embedded in H2 database.")
    @GetMapping("/surfing")
    public ResponseEntity<List<Forecast>> getSurfingWeather() {
        return new ResponseEntity<>(surfingService.getSurfingWeather(), HttpStatus.OK);
    }

    @ApiOperation(value = "Getting forecast data (including surferCoefficient parameter) map for next 16 days for cities embedded in H2 database.")
    @GetMapping("/map")
    public ResponseEntity<Map<LocalDate, List<DailyWeather>>> getDailyWeatherMap() {
        return new ResponseEntity<>(weatherService.getDailyWeather(), HttpStatus.OK);
    }

    @ApiOperation(value = "Getting surfing location with the best surfer coefficient's value for given date.")
    @GetMapping("/best-surfing-location/{date}")
    public ResponseEntity<DailyWeather> getBestSurfingLocationForDate(@PathVariable String date){
        return new ResponseEntity<>(weatherService.getBestSurfingLocationForDate(LocalDate.parse(date)), HttpStatus.OK);
    }

    @ControllerAdvice
    class ExceptionHelper {
        @ExceptionHandler(value = {ApiError.class})
        public ResponseEntity<Object> handleHttpClientErrorException(ApiError exception) {
            return new ResponseEntity<>(exception.getMessage() + exception.getErrorStack(), exception.getStatus());
        }
    }
}
