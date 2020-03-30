package org.kbannach.data.scraper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {
    GDYNIA("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1878");

    private final String meteorogramUrl;
}
