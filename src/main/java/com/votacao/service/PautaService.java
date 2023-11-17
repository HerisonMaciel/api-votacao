package com.votacao.service;


import com.votacao.entity.Pauta;
import com.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;


    public Pauta adicionarPauta(Pauta pauta){
        return pautaRepository.save(pauta);
    }

}
