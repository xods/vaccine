package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.DnevnaStatistika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("dnevnaStatistika")
public interface DnevnaStatistikaRepository extends JpaRepository<DnevnaStatistika, Long> {

}