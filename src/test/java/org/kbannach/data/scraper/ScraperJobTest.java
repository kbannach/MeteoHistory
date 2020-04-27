package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

class ScraperJobTest implements UnitTest {

    @InjectMocks
    private ScraperJob underTest;

    @Mock
    private MeteoDataScraper meteoDataScraper;

    @Test
    void whenRunScrapping_thenInvokeScrapMethod() {
        // when
        underTest.runScrapping();

        // then
        verify(meteoDataScraper).scrap();
    }
}