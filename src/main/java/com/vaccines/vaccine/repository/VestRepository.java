package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.Vest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vest")
public interface VestRepository extends JpaRepository<Vest, Long> {
}