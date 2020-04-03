package org.kbannach.selenium.pages;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.net.URL;

@Component
@RequiredArgsConstructor
class ImageReader {

    @SneakyThrows // TODO throw dedicated exception
    public byte[] readFromUrl(String url) {
        return StreamUtils.copyToByteArray(new URL(url).openStream());
    }
}
