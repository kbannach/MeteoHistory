package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeteoDataScrapper {

    private final MeteoForecastReader meteoForecastReader;
    private final MeteorogramService meteorogramService;

    public void scrap() {
        City city = City.GDYNIA;
        byte[] bytes = meteoForecastReader.readMeteogram(city);
        meteorogramService.persist(bytes, city);
    }

    // TODO job to collect meteorograms
    // TODO controller to download image by date, city(?)
}
