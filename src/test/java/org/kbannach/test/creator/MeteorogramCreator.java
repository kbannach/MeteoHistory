package org.kbannach.test.creator;

import lombok.RequiredArgsConstructor;
import org.kbannach.city.CityName;
import org.kbannach.meteorogram.Meteorogram;
import org.kbannach.meteorogram.MeteorogramRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class MeteorogramCreator {

    private final MeteorogramRepository meteorogramRepository;

    public Meteorogram create(byte[] imageBytes) {
        return meteorogramRepository.save(
                Meteorogram.builder()
                        .cityName(CityName.GDYNIA)
                        .bytes(imageBytes)
                        .build()
        );
    }
}
