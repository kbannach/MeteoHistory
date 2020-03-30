package org.kbannach.meteorogram;

import org.springframework.data.jpa.repository.JpaRepository;

interface MeteorogramRepository extends JpaRepository<Meteorogram, Long> {
}
