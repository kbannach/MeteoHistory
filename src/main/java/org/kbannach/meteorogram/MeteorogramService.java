package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.kbannach.data.scraper.City;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeteorogramService {

    private final MeteorogramRepository meteorogramRepository;

    public void persist(byte[] bytes, City city) {
        meteorogramRepository.save(
                Meteorogram.builder()
                        .bytes(bytes)
                        .city(city)
                        .build()
        );
    }
}
