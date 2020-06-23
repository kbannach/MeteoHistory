package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.kbannach.api.ScrapApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScrapingController implements ScrapApi {

    private final MeteoDataScraper meteoDataScraper;

    @Override
    public ResponseEntity<Void> scrap() {
        meteoDataScraper.scrap();
        return ResponseEntity.ok().build();
    }
}
