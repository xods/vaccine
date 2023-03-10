package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.DnevnaStatistika;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Optional;

public interface DnevnaStatistikaService {
    DnevnaStatistika findByVremeObjavljivanja(@Nullable Date vremeObjavljivanja);
    DnevnaStatistika save(DnevnaStatistika statistika);

}
