package org.kbannach.city;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kbannach.model.GetMeteorogramImageRequest.CityEnum;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum City {
    GDYNIA("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1878", CityEnum.GDYNIA),
    GDANSK("http://www.meteo.pl/um/php/meteorogram_id_um.php?ntype=0u&id=1877", CityEnum.GDANSK);

    private static final Function<CityEnum, Supplier<? extends IllegalArgumentException>> EXCEPTION_SUPPLIER = cityEnum ->
            () -> new IllegalArgumentException(String.format("No %s enum defined for %s.%s", City.class.getCanonicalName(), CityEnum.class.getCanonicalName(), cityEnum));

    private final String meteorogramUrl;
    private final CityEnum enumName;

    public static City findForCityEnum(CityEnum cityEnum) {
        return Arrays.stream(City.values())
                .filter(c -> c.getEnumName().equals(cityEnum))
                .findFirst()
                .orElseThrow(EXCEPTION_SUPPLIER.apply(cityEnum));
    }
}
