package org.kbannach.test.mock;

import lombok.Setter;
import org.kbannach.data.scraper.City;
import org.kbannach.selenium.pages.MeteoForecastReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MockMeteoForecastReader implements MeteoForecastReader {

    @Setter
    private byte[] bytesRead = new byte[0];

    @Override
    public byte[] readMeteogram(City city) {
        return bytesRead;
    }
}
