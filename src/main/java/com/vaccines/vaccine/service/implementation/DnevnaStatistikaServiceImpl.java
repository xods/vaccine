package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.DnevnaStatistika;
import com.vaccines.vaccine.repository.DnevnaStatistikaRepository;
import com.vaccines.vaccine.service.DnevnaStatistikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DnevnaStatistikaServiceImpl implements DnevnaStatistikaService {

    @Autowired
    DnevnaStatistikaRepository statistikaRepository;

    @Override
    public DnevnaStatistika findByVremeObjavljivanja(@Nullable Date vremeObjavljivanja){
        return statistikaRepository.findByVremeObjavljivanja(vremeObjavljivanja);
    }

    @Override
    public DnevnaStatistika save(DnevnaStatistika statistika){
        return statistikaRepository.save(statistika);
    }
}
