package org.kbannach.test.mock;

import lombok.Setter;
import org.kbannach.city.City;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Primary
public class MockMeteoForecastReader implements MeteoForecastReader {

    @Setter
    private byte[] bytesRead;

    @Setter
    private Map<City, byte[]> cityImageMap;

    @Override
    public Optional<byte[]> readMeteogram(City city) {
        return Optional.of(
                Optional.ofNullable(bytesRead)
                        .orElseGet(() -> Objects.requireNonNull(cityImageMap.get(city)))
        );
    }
}
