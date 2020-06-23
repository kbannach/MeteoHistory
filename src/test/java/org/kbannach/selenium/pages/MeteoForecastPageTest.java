package org.kbannach.selenium.pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.City;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeteoForecastPageTest implements UnitTest {

    private static final byte[] READ_BYTES = new byte[]{1, 2, 3};

    @InjectMocks
    private MeteoForecastPage underTest;

    @Mock
    private WebDriverFactory webDriverFactory;
    @Mock
    private ImageReader imageReader;

    @Mock
    private WebDriver driverMock;
    @Mock
    private WebElement readSuccessElement;
    @Mock
    private WebElement readFailureElement;

    @BeforeEach
    void setUp() {
        when(webDriverFactory.get()).thenReturn(driverMock);
    }

    @Test
    void givenWebDriverAndOneCity_whenReadAllMeteorograms_thenReturnBytesForExactlyOneCity() {
        // given
        City city = City.GDYNIA;

        when(driverMock.findElement(any())).thenReturn(readSuccessElement);

        String url = "mockUrl";
        when(readSuccessElement.getAttribute("src")).thenReturn(url);
        when(imageReader.readFromUrl(eq(url))).thenReturn(READ_BYTES);

        // when
        Map<City, byte[]> meteograms = underTest.readAllMeteograms(Set.of(city));

        // then
        assertThat(meteograms)
                .hasSize(1)
                .containsEntry(City.GDYNIA, READ_BYTES);

        verify(driverMock).get(city.getMeteorogramUrl());
        verify(imageReader).readFromUrl(url);
    }

    @Test
    void givenAllCities_whenReadAllMeteorograms_thenReturnBytesForEveryCity() {
        // given
        Set<City> cities = Set.of(City.values());

        when(driverMock.findElement(any())).thenReturn(readSuccessElement);

        String url = "mockUrl";
        when(readSuccessElement.getAttribute("src")).thenReturn(url);
        when(imageReader.readFromUrl(eq(url))).thenReturn(READ_BYTES);

        // when
        Map<City, byte[]> meteograms = underTest.readAllMeteograms(cities);

        // then
        assertThat(meteograms)
                .hasSize(cities.size())
                .containsOnlyKeys(cities);

        cities.forEach(city -> verify(driverMock).get(city.getMeteorogramUrl()));
        verify(imageReader, times(cities.size())).readFromUrl(url);
    }

    @Test
    void givenTwoCitiesAndOneReadingFailure_whenReadAllMeteorograms_thenReturnBytesForSucceededCity() {
        // given
        List<City> cities = List.of(City.GDYNIA, City.GDANSK);

        when(driverMock.findElement(any()))
                .thenReturn(readSuccessElement) // for City.GDYNIA
                .thenReturn(readFailureElement); // for City.GDANSK

        String url = "mockUrl";
        when(readSuccessElement.getAttribute("src")).thenReturn(url);
        when(imageReader.readFromUrl(eq(url))).thenReturn(READ_BYTES);

        when(readFailureElement.getAttribute("src")).thenThrow(RuntimeException.class);

        // when
        Map<City, byte[]> meteograms = underTest.readAllMeteograms(cities);

        // then
        assertThat(meteograms)
                .hasSize(1)
                .containsEntry(City.GDYNIA, READ_BYTES)
                .doesNotContainKey(City.GDANSK);

        cities.forEach(city -> verify(driverMock).get(city.getMeteorogramUrl()));
        verify(imageReader).readFromUrl(url);
    }

    @Test
    void givenWebDriver_whenReadMeteorogramByCity_thenQuitDriver() {
        // given
        when(driverMock.findElement(any())).thenReturn(readSuccessElement);
        byte[] doesNotMatter = {0};
        when(imageReader.readFromUrl(any())).thenReturn(doesNotMatter);

        Set<City> cities = Set.of(City.values());

        // when
        underTest.readAllMeteograms(cities);

        // then
        verify(driverMock).quit();
    }

    @Test
    void givenFailingWebDriver_whenReadMeteorogramByCity_thenReturnNullAndQuitDriver() {
        // given
        when(driverMock.findElement(any())).thenThrow(new IllegalStateException());

        Set<City> cities = Set.of(City.values());

        // when
        Map<City, byte[]> result = underTest.readAllMeteograms(cities);

        // then
        assertThat(result).isEmpty();
        verify(driverMock).quit();
    }

    @Test
    void givenWebElementNotFound_whenReadMeteorogramByCity_thenReturnNullAndQuitDriver() {
        // given
        when(driverMock.findElement(any())).thenReturn(null);

        Set<City> cities = Set.of(City.values());

        // when
        Map<City, byte[]> result = underTest.readAllMeteograms(cities);

        // then
        assertThat(result).isEmpty();
        verify(driverMock).quit();
    }
}