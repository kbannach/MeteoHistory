package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.city.City;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MeteoDataScraper {

    private final MeteoForecastReader meteoForecastReader;
    private final MeteorogramService meteorogramService;

    public void scrap() {
        Arrays.stream(City.values())
                .forEach(city ->
                        meteoForecastReader.readMeteogram(city)
                                .ifPresent(bytes -> meteorogramService.persist(bytes, city))
                );
    }
}
