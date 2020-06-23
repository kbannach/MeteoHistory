package org.kbannach.test.mock;

import lombok.Setter;
import org.kbannach.city.City;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@Primary
public class MockMeteoForecastReader implements MeteoForecastReader {

    @Setter
    private Map<City, byte[]> cityImageMap;

    @Override
    public Map<City, byte[]> readAllMeteograms(Collection<City> cities) {
        return cityImageMap;
    }
}
