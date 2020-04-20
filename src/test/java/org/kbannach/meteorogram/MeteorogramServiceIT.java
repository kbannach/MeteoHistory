package org.kbannach.meteorogram;

import org.junit.jupiter.api.Test;
import org.kbannach.IntegrationTest;
import org.kbannach.city.CityName;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MeteorogramServiceIT extends IntegrationTest {

    @Autowired
    private MeteorogramService underTest;

    @Test
    void givenBytesAndCity_whenPersist_thenPersist() {
        // given
        LocalDateTime now = LocalDateTime.now();
        byte[] bytes = {1, 2, 3};
        CityName cityName = CityName.GDYNIA;

        // when
        Meteorogram persisted = underTest.persist(bytes, cityName);

        // then
        Meteorogram actualPersisted = findById(Meteorogram.class, persisted.getId());

        assertArrayEquals(bytes, actualPersisted.getBytes());
        assertEquals(cityName, actualPersisted.getCityName());
        assertThat(actualPersisted.getCreationDateTime()).isCloseTo(now, within(1, ChronoUnit.SECONDS));
    }
}