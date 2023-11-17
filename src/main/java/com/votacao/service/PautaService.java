package com.votacao.service;


import com.votacao.entity.Pauta;
import com.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta adicionarPauta(Pauta pauta, LocalDateTime now){
        return pautaRepository.save(pauta);
    }

    public Optional<Pauta> getPauta(Integer id) {
        return pautaRepository.findById(id);
    }
}
