package org.kbannach.data.scraper;

import org.junit.jupiter.api.Test;
import org.kbannach.IntegrationTest;
import org.kbannach.city.City;
import org.kbannach.meteorogram.Meteorogram;
import org.kbannach.test.mock.MockMeteoForecastReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScrapingControllerIT extends IntegrationTest {

    @Autowired
    private MockMeteoForecastReader mockMeteoForecastReader;

    @Test
    void givenImagesForCities_whenScrap_thenCreateMeteorogramForEachCity() throws Exception {
        // given
        String url =
//                "/api" + TODO to fix
                "/scrap";

        byte[] gdyniaBytes = {1, 2, 3};
        byte[] gdanskBytes = {11, 22, 33};
        Map<City, byte[]> cityMap = Map.of(
                City.GDYNIA, gdyniaBytes,
                City.GDANSK, gdanskBytes
        );
        mockMeteoForecastReader.setCityImageMap(cityMap);

        // when
        ResultActions resultActions = mockMvc.perform(post(url));

        // then
        resultActions.andExpect(status().isOk());

        List<Meteorogram> meteorograms = transactionWrapper.executeInReadOnlyTransactionAndReturn(em ->
                em.createQuery("select m from Meteorogram m", Meteorogram.class)
                        .getResultList()
        );

        Map<City, byte[]> map = meteorograms.stream()
                .collect(Collectors.toMap(
                        Meteorogram::getCity,
                        Meteorogram::getBytes
                ));
        assertThat(map)
                .hasSize(2)
                .containsEntry(City.GDYNIA, gdyniaBytes)
                .containsEntry(City.GDANSK, gdanskBytes);
    }
}