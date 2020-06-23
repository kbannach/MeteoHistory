package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.City;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static org.mockito.Mockito.verify;

class MeteoDataScraperTest implements UnitTest {

    @InjectMocks
    private MeteoDataScraper underTest;

    @Mock
    private MeteoForecastReader meteoForecastReader;
    @Mock
    @SuppressWarnings("unused")
    private MeteorogramService meteorogramService;

    @Test
    void whenScrap_thenReadMeteorogramForEveryCity() {
        // given
        Set<City> cities = Set.of(City.values());

        // when
        underTest.scrap();

        // then
        verify(meteoForecastReader).readAllMeteograms(cities);
    }
}