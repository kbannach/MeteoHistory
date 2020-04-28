package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class ScrapingControllerTest implements UnitTest {

    @InjectMocks
    private ScrapingController underTest;

    @Mock
    private MeteoDataScraper meteoDataScraper;

    @Test
    void whenScrap_thenDoScrapAndReturnOk() {
        // when
        ResponseEntity<Void> responseEntity = underTest.scrap();

        // then
        verify(meteoDataScraper).scrap();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }
}