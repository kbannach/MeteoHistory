package org.kbannach.city;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {
    GDYNIA("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1878"),
    GDANSK("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1877");

    private final String meteorogramUrl;
}
