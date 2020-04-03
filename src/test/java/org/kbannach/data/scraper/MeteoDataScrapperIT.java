package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.IntegrationTest;
import org.kbannach.meteorogram.Meteorogram;
import org.kbannach.test.mock.MockMeteoForecastReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MeteoDataScrapperIT extends IntegrationTest {

    @Autowired
    private MeteoDataScrapper underTest;

    @Autowired
    private MockMeteoForecastReader mockMeteorogramReader;

    @Test
    void givenMeteorogramBytes_whenScrap_thenPersistOneMeteorogramWithCityGdyniaAndGivenBytes() {
        // given
        byte[] bytesRead = {1, 2, 3};
        mockMeteorogramReader.setBytesRead(bytesRead);

        // when
        underTest.scrap();

        // then
        List<Meteorogram> meteorograms = transactionWrapper.executeInReadOnlyTransactionAndReturn(em ->
                em.createQuery("select m from Meteorogram m", Meteorogram.class).getResultList()
        );

        assertThat(meteorograms)
                .hasSize(1)
                .allMatch(m -> Arrays.equals(m.getBytes(), bytesRead))
                .allMatch(m -> m.getCity() == City.GDYNIA);
    }
}