package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(MeteogramsController.BASE_URL)
public class MeteogramsController {

    public static final String BASE_URL = "/api/meteorogram";
    public static final String GET_METEOROGRAM_IMAGE_URL = "/image";

    private final MeteorogramService meteorogramService;

    // TODO endpoint download an array of images by a range of dates and a city (range of cities?)

    @GetMapping(value = GET_METEOROGRAM_IMAGE_URL, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE /*TODO maybe change to image*/)
    public ResponseEntity<byte[]> getMeteorogramImage(@Valid @RequestBody GetMeteorogramImageRequest request) {
        return ResponseEntity.ok(meteorogramService.getMeteorogramImage(request));
    }
}
