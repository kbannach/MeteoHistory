package org.kbannach.meteorogram;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kbannach.data.scraper.City;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
//@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
class Meteorogram {
    // TODO DB login properties (H2 for a start)

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime creationDateTime;

    @NotNull
    @Lob
    private byte[] bytes;

    @NotNull
    @Enumerated(EnumType.STRING)
    private City city;
}
