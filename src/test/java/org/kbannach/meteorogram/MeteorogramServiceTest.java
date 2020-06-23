package org.kbannach.meteorogram;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.city.City;
import org.kbannach.model.GetMeteorogramImageRequest;
import org.kbannach.model.GetMeteorogramImageRequest.CityEnum;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeteorogramServiceTest implements UnitTest {

    @InjectMocks
    private MeteorogramService underTest;

    @Mock
    private MeteorogramRepository meteorogramRepository;

    @Captor
    private ArgumentCaptor<Meteorogram> meteorogramCaptor;
    @Captor
    private ArgumentCaptor<LocalDateTime> creationDateTimeCaptor;

    @Test
    void givenBytesAndCity_whenPersist_thenCallRepositoryWithProperArguments() {
        // given
        byte[] bytes = new byte[]{1, 2, 3};
        City cityName = City.GDYNIA;

        // when
        underTest.persist(bytes, cityName);

        // then
        verify(meteorogramRepository).save(meteorogramCaptor.capture());

        Meteorogram actual = meteorogramCaptor.getValue();
        assertEquals(bytes, actual.getBytes());
        assertEquals(cityName, actual.getCity());
    }

    @Test
    void givenMeteorogramNotFound_whenGetMeteorogramImage_thenThrowEntityNotFound() {
        // given
        CityEnum city = CityEnum.GDYNIA;
        LocalDateTime dateTime = LocalDateTime.now();
        GetMeteorogramImageRequest request = new GetMeteorogramImageRequest()
                .city(city)
                .dateTime(dateTime);

        when(meteorogramRepository.findBytesByCreationDateTimeAndCity(eq(dateTime), eq(City.findForCityEnum(city)), any())).thenReturn(Page.empty());

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> underTest.getMeteorogramImage(request);

        // then
        assertThatThrownBy(throwingCallable)
                .isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void givenMeteorogramExists_whenGetMeteorogramImage_thenReturnBytes() {
        // given
        CityEnum city = CityEnum.GDYNIA;
        LocalDateTime dateTime = LocalDateTime.now();
        GetMeteorogramImageRequest request = new GetMeteorogramImageRequest()
                .city(city)
                .dateTime(dateTime);

        byte[] expectedBytes = {1, 2, 3};
        when(meteorogramRepository.findBytesByCreationDateTimeAndCity(eq(dateTime), eq(City.findForCityEnum(city)), any())).thenReturn(new PageImpl<>(
                List.of(
                        Meteorogram.builder()
                                .bytes(expectedBytes)
                                .build()
                )
        ));

        // when
        byte[] actualBytes = underTest.getMeteorogramImage(request);

        // then
        assertThat(actualBytes)
                .isEqualTo(expectedBytes);
    }

    @Test
    void givenNoDateTimeSpecified_whenGetMeteorogramImage_thenFindMeteorogramWithNowDate() {
        // given
        CityEnum city = CityEnum.GDYNIA;
        GetMeteorogramImageRequest request = new GetMeteorogramImageRequest()
                .city(city);

        when(meteorogramRepository.findBytesByCreationDateTimeAndCity(any(), any(), any())).thenReturn(new PageImpl<>(
                List.of(Meteorogram.builder().build())
        ));

        // when
        underTest.getMeteorogramImage(request);

        // then
        verify(meteorogramRepository).findBytesByCreationDateTimeAndCity(creationDateTimeCaptor.capture(), eq(City.findForCityEnum(city)), any());

        assertThat(creationDateTimeCaptor.getValue())
                .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }
}