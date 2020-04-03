package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.kbannach.data.scraper.City;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeteorogramService {

    private final MeteorogramRepository meteorogramRepository;

    public Meteorogram persist(byte[] bytes, City city) {
        return meteorogramRepository.save(
                Meteorogram.builder()
                        .bytes(bytes)
                        .city(city)
                        .build()
        );
    }
}
