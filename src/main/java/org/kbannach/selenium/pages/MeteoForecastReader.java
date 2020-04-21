package org.kbannach.selenium.pages;

import org.kbannach.city.City;

public interface MeteoForecastReader {

    byte[] readMeteogram(City cityName);
}
