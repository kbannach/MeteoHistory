package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ScrapingController.BASE_URL)
public class ScrapingController {

    public static final String BASE_URL = "/api/scrap";

    private final MeteoDataScraper meteoDataScraper;

    @PostMapping
    public ResponseEntity<Void> scrap() {
        meteoDataScraper.scrap();
        return ResponseEntity.ok().build();
    }
}
