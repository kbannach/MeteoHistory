package org.kbannach.city;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CityName {
    GDYNIA("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1878");

    private final String meteorogramUrl;
}
