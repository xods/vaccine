package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.DnevnaStatistika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("dnevnaStatistika")
public interface DnevnaStatistikaRepository extends JpaRepository<DnevnaStatistika, Long> {
    DnevnaStatistika findByVremeObjavljivanja(@Nullable Date vremeObjavljivanja);
}