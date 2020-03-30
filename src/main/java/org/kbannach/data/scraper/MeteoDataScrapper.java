package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastPage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeteoDataScrapper {

    private final MeteoForecastPage meteoForecastPage;
    private final MeteorogramService meteorogramService;

    public void scrap() {
        City city = City.GDYNIA;
        byte[] bytes = meteoForecastPage.readMeteogram(city);
        meteorogramService.persist(bytes, city);
    }

    // TODO job to collect meteorograms
    // TODO controller to download image by date, city(?)
}
