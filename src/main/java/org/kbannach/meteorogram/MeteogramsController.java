package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.kbannach.api.MeteorogramApi;
import org.kbannach.model.GetMeteorogramImageRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MeteogramsController implements MeteorogramApi {

    private final MeteorogramService meteorogramService;

    // TODO endpoint download an array of images by a range of dates and a city (range of cities?)

    @Override
    public ResponseEntity<Resource> getMeteorogramImage(@Valid @RequestBody GetMeteorogramImageRequest request) {
        byte[] bytes = meteorogramService.getMeteorogramImage(request);
        return ResponseEntity.ok(new ByteArrayResource(bytes));
    }
}
