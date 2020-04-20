package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

class ScrapperJobTest implements UnitTest {

    @InjectMocks
    private ScrapperJob underTest;

    @Mock
    private MeteoDataScrapper meteoDataScrapper;

    @Test
    void whenRunScrapping_thenInvokeScrapMethod() {
        // when
        underTest.runScrapping();

        // then
        verify(meteoDataScrapper).scrap();
    }
}