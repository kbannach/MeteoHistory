package org.kbannach.meteorogram;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.CityName;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class MeteorogramServiceTest implements UnitTest {

    @InjectMocks
    private MeteorogramService underTest;

    @Mock
    private MeteorogramRepository meteorogramRepository;

    @Captor
    private ArgumentCaptor<Meteorogram> meteorogramCaptor;

    @Test
    void givenBytesAndCity_whenPersist_thenCallRepositoryWithProperArguments() {
        // given
        byte[] bytes = new byte[]{1, 2, 3};
        CityName cityName = CityName.GDYNIA;

        // when
        underTest.persist(bytes, cityName);

        // then
        verify(meteorogramRepository).save(meteorogramCaptor.capture());

        Meteorogram actual = meteorogramCaptor.getValue();
        assertEquals(bytes, actual.getBytes());
        assertEquals(cityName, actual.getCityName());
    }
}