package org.kbannach.selenium.pages;

import org.kbannach.city.CityName;

public interface MeteoForecastReader {

    byte[] readMeteogram(CityName cityName);
}
