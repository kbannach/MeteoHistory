package org.kbannach.meteorogram;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meteorogram")
public class MeteogramsController {

    @GetMapping
    public ResponseEntity<Meteorogram> get() {
        // TODO get by date, city
        return ResponseEntity.ok().build();
    }
}
