package org.kbannach.test.mock;

import lombok.Setter;
import org.kbannach.data.scraper.City;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@Primary
public class MockMeteoForecastReader implements MeteoForecastReader {

    @Setter
    private byte[] bytesRead;

    @Setter
    private Map<City, byte[]> cityImageMap;

    @Override
    public byte[] readMeteogram(City city) {
        return Objects.requireNonNullElseGet(
                bytesRead,
                () -> Objects.requireNonNull(cityImageMap.get(city))
        );
    }
}
