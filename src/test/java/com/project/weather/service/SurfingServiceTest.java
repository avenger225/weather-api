package com.project.weather.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@WithMockUser
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SurfingServiceTest {

    @Autowired
    private SurfingService surfingService;

    @Test
    void shouldReturnTrueForSuitableWeather() {
        boolean suitableForSurfing = surfingService.weatherSuitableForSurfing(6, 26);
        assertThat(suitableForSurfing).isTrue();
    }

    @Test
    void shouldReturnFalseForNotSuitableWeather() {
        boolean suitableForSurfing = surfingService.weatherSuitableForSurfing(1, 5);
        assertThat(suitableForSurfing).isFalse();
    }


    @Test
    void shouldReturnTrueForNotTooLowTemperature() {
        boolean suitableForSurfing = surfingService.isTemperatureNotTooLow(16);
        assertThat(suitableForSurfing).isTrue();
    }

    @Test
    void shouldReturnFalseForTooLowTemperature() {
        boolean suitableForSurfing = surfingService.isTemperatureNotTooLow(2);
        assertThat(suitableForSurfing).isFalse();
    }

    @Test
    void shouldReturnTrueForNotTooHighTemperature() {
        boolean suitableForSurfing = surfingService.isTemperatureNotTooHigh(17);
        assertThat(suitableForSurfing).isTrue();
    }

    @Test
    void shouldReturnFalseForTooHighTemperature() {
        boolean suitableForSurfing = surfingService.isTemperatureNotTooHigh(40);
        assertThat(suitableForSurfing).isFalse();
    }

    @Test
    void shouldReturnTrueForNotTooWeakWind() {
        boolean suitableForSurfing = surfingService.isWindNotTooWeak(6);
        assertThat(suitableForSurfing).isTrue();
    }

    @Test
    void shouldReturnFalseForTooWeakWind() {
        boolean suitableForSurfing = surfingService.isWindNotTooWeak(2);
        assertThat(suitableForSurfing).isFalse();
    }

    @Test
    void shouldReturnTrueForNotTooStringWind() {
        boolean suitableForSurfing = surfingService.isWindNotTooStrong(16);
        assertThat(suitableForSurfing).isTrue();
    }

    @Test
    void shouldReturnFalseForTooStringWind() {
        boolean suitableForSurfing = surfingService.isWindNotTooStrong(30);
        assertThat(suitableForSurfing).isFalse();
    }
}
