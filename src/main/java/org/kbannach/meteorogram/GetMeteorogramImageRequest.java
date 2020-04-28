package org.kbannach.meteorogram;

import lombok.Builder;
import lombok.Value;
import org.kbannach.city.City;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class GetMeteorogramImageRequest {

    LocalDateTime dateTime;

    @NotNull
    City city;
}
