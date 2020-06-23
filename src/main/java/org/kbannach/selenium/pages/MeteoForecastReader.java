package org.kbannach.selenium.pages;

import org.kbannach.city.City;

import java.util.Collection;
import java.util.Map;

public interface MeteoForecastReader {

    Map<City, byte[]> readAllMeteograms(Collection<City> cities);
}
