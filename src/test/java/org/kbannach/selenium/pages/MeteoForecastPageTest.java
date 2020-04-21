package org.kbannach.selenium.pages;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.City;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeteoForecastPageTest implements UnitTest {

    @InjectMocks
    private MeteoForecastPage underTest;

    @Mock
    private WebDriverFactory webDriverFactory;
    @Mock
    private ImageReader imageReader;

    @Test
    void givenWebDriver_whenReadMeteorogramByCity_thenReturnBytesReadByImageReader() {
        // given
        City city = City.GDYNIA;

        WebDriver driverMock = mock(WebDriver.class);
        when(webDriverFactory.get()).thenReturn(driverMock);

        WebElement webElementMock = mock(WebElement.class);
        when(driverMock.findElement(any())).thenReturn(webElementMock);

        String url = "mockUrl";
        when(webElementMock.getAttribute("src")).thenReturn(url);

        byte[] readBytes = {1, 2, 3};
        when(imageReader.readFromUrl(eq(url))).thenReturn(readBytes);

        // when
        byte[] actualBytes = underTest.readMeteogram(city);

        // then
        assertEquals(readBytes, actualBytes);

        verify(driverMock).get(city.getMeteorogramUrl());
        verify(imageReader).readFromUrl(url);
    }

    @Test
    void givenWebDriver_whenReadMeteorogramByCity_thenQuitDriver() {
        // given
        WebDriver driverMock = mock(WebDriver.class);
        when(webDriverFactory.get()).thenReturn(driverMock);

        WebElement webElementMock = mock(WebElement.class);
        when(driverMock.findElement(any())).thenReturn(webElementMock);

        // when
        underTest.readMeteogram(City.GDYNIA);

        // then
        verify(driverMock).quit();
    }

    @Test
    void givenFailingWebDriver_whenReadMeteorogramByCity_thenQuitDriver() {
        // given
        WebDriver driverMock = mock(WebDriver.class);
        when(webDriverFactory.get()).thenReturn(driverMock);

        when(driverMock.findElement(any())).thenThrow(new IllegalStateException());

        // when
        ThrowingCallable throwingCallable = () -> underTest.readMeteogram(City.GDYNIA);

        // then
        assertThatThrownBy(throwingCallable).isExactlyInstanceOf(IllegalStateException.class);
        verify(driverMock).quit();
    }

    @Test
    void givenWebElementNotFound_whenReadMeteorogramByCity_thenThrowNullPointerExceptionAndQuitDriver() {
        // given
        WebDriver driverMock = mock(WebDriver.class);
        when(webDriverFactory.get()).thenReturn(driverMock);

        when(driverMock.findElement(any())).thenReturn(null);

        // when
        ThrowingCallable throwingCallable = () -> underTest.readMeteogram(City.GDYNIA);

        // then
        assertThatThrownBy(throwingCallable).isExactlyInstanceOf(NullPointerException.class);
        verify(driverMock).quit();
    }
}