package com.project.weather.comparator;

import com.project.weather.webclient.dto.DailyWeather;

import java.util.Comparator;

public class SurfingDailyWeatherComparator implements Comparator<DailyWeather> {

    @Override
    public int compare(DailyWeather dailyWeather1, DailyWeather dailyWeather2) {
        return Double.compare(dailyWeather1.getSurferCoefficient(), dailyWeather2.getSurferCoefficient());
    }
}
