package org.kbannach.data.scraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperJob {

    private final MeteoDataScraper meteoDataScraper;

    @Scheduled(cron = "${app.scrapping-cron}")
    public void runScrapping() {
        log.info("running scheduled scrapping...");
        meteoDataScraper.scrap();
        log.info("scrapping complete");
    }
}
