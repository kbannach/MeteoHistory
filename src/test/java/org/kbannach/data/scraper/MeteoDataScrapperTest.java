package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.City;
import org.kbannach.meteorogram.MeteorogramService;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
    void whenScrap_thenReadAndPersistMeteorogramForEveryCity() {
        // given
        int citiesCount = City.values().length;

        // when
        underTest.scrap();

        // then
        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        verify(meteoForecastReader, times(citiesCount)).readMeteogram(cityCaptor.capture());
        assertThat(cityCaptor.getAllValues())
                .containsExactly(City.values());

        cityCaptor = ArgumentCaptor.forClass(City.class);
        verify(meteorogramService, times(citiesCount)).persist(any(), cityCaptor.capture());
        assertThat(cityCaptor.getAllValues())
                .containsExactly(City.values());
    }

    @Test
    void givenBytesReadForEveryCity_whenScrap_thenPersistMeteorogramWithGivenBytesForEveryCity() {
        // given
        City[] cities = City.values();
        Map<City, byte[]> cityImageMap = IntStream.range(0, cities.length)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(cities[i], new byte[]{(byte) (1 + i), (byte) (2 + i), (byte) (3 + i)}))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        cityImageMap.forEach((city, bytes) -> when(meteoForecastReader.readMeteogram(city)).thenReturn(bytes));

        // when
        underTest.scrap();

        // then
        cityImageMap.forEach(((city, bytes) -> verify(meteorogramService).persist(bytes, city)));
    }
}