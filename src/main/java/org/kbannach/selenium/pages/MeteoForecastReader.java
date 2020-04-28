package org.kbannach.selenium.pages;

import org.kbannach.city.City;

import java.util.Optional;

public interface MeteoForecastReader {

    Optional<byte[]> readMeteogram(City cityName);
}
