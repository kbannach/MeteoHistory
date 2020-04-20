package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.city.CityName;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeteoDataScrapper {

    private final MeteoForecastReader meteoForecastReader;
    private final MeteorogramService meteorogramService;

    public void scrap() {
        CityName cityName = CityName.GDYNIA;
        byte[] bytes = meteoForecastReader.readMeteogram(cityName);
        meteorogramService.persist(bytes, cityName);
    }

    // TODO automate opening different cities(?) (cities list could be fetched from DB)

    // TODO controller to run scraping manually
}
