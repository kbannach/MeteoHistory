package org.kbannach.selenium.pages;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.kbannach.UnitTest;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ImageReaderTest implements UnitTest {

    private static final String METEOROGRAM_IMAGE_RESOURCE_NAME = "gdynia_mgram_pict.png";

    @InjectMocks
    private ImageReader underTest;

    @Test
    void givenMeteorogramImage_whenReadFromUrl_thenReadExactBytes() throws IOException {
        // given
        URL resource = IOUtils.resourceToURL(METEOROGRAM_IMAGE_RESOURCE_NAME, this.getClass().getClassLoader());
        byte[] expectedBytes = IOUtils.toByteArray(resource);
        String url = resource.toString();

        // when
        byte[] actualBytes = underTest.readFromUrl(url);

        // then
        assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    void givenMalformedUrl_whenReadFromUrl_thenThrowMalformedURLException() {
        // given
        String url = "malformed.url()";

        // when
        ThrowingCallable throwingCallable = () -> underTest.readFromUrl(url);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(MalformedURLException.class);
    }
}