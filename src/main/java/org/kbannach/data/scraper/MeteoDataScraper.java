package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.city.City;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MeteoDataScraper {

    private final MeteoForecastReader meteoForecastReader;
    private final MeteorogramService meteorogramService;

    public void scrap() {
        meteoForecastReader.readAllMeteograms(Set.of(City.values()))
                .forEach(((city, bytes) -> meteorogramService.persist(bytes, city)));
    }
}
