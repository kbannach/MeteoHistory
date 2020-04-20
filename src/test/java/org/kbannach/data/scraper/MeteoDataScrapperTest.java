package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.CityName;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeteoDataScrapperTest implements UnitTest {

    @InjectMocks
    private MeteoDataScrapper underTest;

    @Mock
    private MeteoForecastReader meteoForecastReader;
    @Mock
    private MeteorogramService meteorogramService;

    @Test
    void givenBytesFromMeteoPage_whenScrap_thenPersistBytesForGdynia() {
        // given
        byte[] bytes = {1, 2, 3};
        when(meteoForecastReader.readMeteogram(any())).thenReturn(bytes);

        // when
        underTest.scrap();

        // then
        verify(meteoForecastReader).readMeteogram(CityName.GDYNIA);
        verify(meteorogramService).persist(bytes, CityName.GDYNIA);
    }
}