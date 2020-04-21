package org.kbannach.meteorogram;

import org.junit.jupiter.api.Test;
import org.kbannach.IntegrationTest;
import org.kbannach.test.creator.MeteorogramCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeteogramsControllerIT extends IntegrationTest {

    @Autowired
    private MeteorogramCreator meteorogramCreator;

    @Test
    void givenMeteorogramWithImage_whenGetMeteorogramImage_thenReturnImageBytes() throws Exception {
        // given
        String url = MeteogramsController.BASE_URL + MeteogramsController.GET_METEOROGRAM_IMAGE_URL;

        byte[] imageBytes = "someImage".getBytes();
        Meteorogram meteorogram = meteorogramCreator.create(imageBytes);

        GetMeteorogramImageRequest request = GetMeteorogramImageRequest.builder()
                .city(meteorogram.getCity())
                .dateTime(meteorogram.getCreationDateTime().plusMinutes(1))
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(mockMvcHelper.getWithBody(url, request));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(imageBytes));
    }
}