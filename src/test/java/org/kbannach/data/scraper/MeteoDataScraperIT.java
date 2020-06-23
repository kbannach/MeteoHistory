package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.IntegrationTest;
import org.kbannach.city.City;
import org.kbannach.meteorogram.Meteorogram;
import org.kbannach.test.mock.MockMeteoForecastReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MeteoDataScraperIT extends IntegrationTest {

    @Autowired
    private MeteoDataScraper underTest;

    @Autowired
    private MockMeteoForecastReader mockMeteorogramReader;

    @Test
    void givenMeteorogramBytes_whenScrap_thenPersistOneMeteorogramWithCityGdyniaAndGivenBytes() {
        // given
        City[] cities = City.values();
        Map<City, byte[]> cityImageMap = IntStream.range(0, cities.length)
                .mapToObj(i -> new SimpleEntry<>(cities[i], new byte[]{(byte) (1 + i), (byte) (2 + i), (byte) (3 + i)}))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

        mockMeteorogramReader.setCityImageMap(cityImageMap);

        // when
        underTest.scrap();

        // then
        List<Meteorogram> meteorograms = transactionWrapper.executeInReadOnlyTransactionAndReturn(em ->
                em.createQuery("select m from Meteorogram m", Meteorogram.class).getResultList()
        );

        assertThat(meteorograms)
                .hasSize(cities.length)
                .allMatch(m -> Arrays.equals(m.getBytes(), cityImageMap.get(m.getCity())))
                .extracting(Meteorogram::getCity)
                .containsExactlyInAnyOrder(cities);
    }
}