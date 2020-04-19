package org.kbannach.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
@RequiredArgsConstructor
public class MockMvcHelper {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public MockHttpServletRequestBuilder getWithBody(String url, Object body) {
        return get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));
    }
}
