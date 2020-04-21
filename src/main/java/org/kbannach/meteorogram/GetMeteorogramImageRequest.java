package org.kbannach.meteorogram;

import lombok.Builder;
import lombok.Value;
import org.kbannach.city.City;

import java.time.LocalDateTime;

@Value
@Builder
public class GetMeteorogramImageRequest {

    LocalDateTime dateTime;
    City city;
}
