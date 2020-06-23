package org.kbannach.city;

import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.kbannach.model.GetMeteorogramImageRequest.CityEnum;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest implements UnitTest {

    @Test
    void givenCityEnum_whenFindForCityEnum_thenReturnCorrespondingCity() {
        // given
        Stream.of(CityEnum.values())
                .forEach(cityEnum -> {
                    // when
                    City city = City.findForCityEnum(cityEnum);

                    // then
                    assertThat(city.getEnumName()).isEqualTo(cityEnum);
                });
    }

    @Test
    void thereAreEqualAmountOfSpecifiedCityEnumsAndCitiesTest() {
        // given
        int cityEnumsCount = CityEnum.values().length;
        int citiesCount = City.values().length;

        // then
        assertThat(cityEnumsCount).isEqualTo(citiesCount);
    }
}