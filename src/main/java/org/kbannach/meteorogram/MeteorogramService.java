package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.kbannach.city.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeteorogramService {

    private final MeteorogramRepository meteorogramRepository;

    public byte[] getMeteorogramImage(GetMeteorogramImageRequest request) {
        LocalDateTime toDate = Objects.requireNonNullElse(request.getDateTime(), LocalDateTime.now());

        Page<Meteorogram> page = meteorogramRepository.findBytesByCreationDateTimeAndCity(toDate, request.getCity(), PageRequest.of(0, 1));
        Meteorogram meteorogram = page.get()
                .findAny()
                .orElseThrow(EntityNotFoundException::new);

        return meteorogram.getBytes();
    }

    public Meteorogram persist(byte[] bytes, City city) {
        return meteorogramRepository.save(
                Meteorogram.builder()
                        .bytes(bytes)
                        .city(city)
                        .build()
        );
    }
}
