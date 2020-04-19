package org.kbannach.meteorogram;

import org.kbannach.data.scraper.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MeteorogramRepository extends JpaRepository<Meteorogram, Long> {

    @Query("select m " +
            "from Meteorogram m " +
            "where m.city = :city " +
            "and m.creationDateTime <= :creationDateTime " +
            "order by m.creationDateTime desc")
    Page<Meteorogram> findBytesByCreationDateTimeAndCity(@Param("creationDateTime") LocalDateTime creationDateTime, @Param("city") City city, Pageable pageRequest);
}
