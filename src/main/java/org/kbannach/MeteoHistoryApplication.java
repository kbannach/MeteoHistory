package org.kbannach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MeteoHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeteoHistoryApplication.class, args);
    }
}
