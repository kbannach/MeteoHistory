package org.kbannach.data.scraper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {
    GDYNIA("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1878"),
    GDANSK("http://www.meteo.pl/um/php/meteorogram_list.php?ntype=0u&fdate=2020042112&row=346&col=210&lang=pl&cname=Gda%F1sk");

    private final String meteorogramUrl;
}
