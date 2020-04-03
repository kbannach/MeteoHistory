package org.kbannach.selenium.pages;

import org.kbannach.data.scraper.City;

public interface MeteoForecastReader {

    byte[] readMeteogram(City city);
}
