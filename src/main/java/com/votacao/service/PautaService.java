package com.votacao.service;


import com.votacao.entity.Pauta;
import com.votacao.entity.Voto;
import com.votacao.enuns.MensagemException;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    public Pauta adicionarPauta(Pauta pauta){
        return pautaRepository.save(pauta);
    }

    public Optional<Pauta> getPauta(Integer id) {
        Optional<Pauta> pauta = Optional.ofNullable(pautaRepository.findById(id)
                .orElseThrow(() -> new ExceptionVotacao(MensagemException.PAUTA_NAO_ENCONTRADA, HttpStatus.NO_CONTENT)));
        return pauta;
    }

    public List<Pauta> getPautas() {
        return pautaRepository.findAll();
    }

    public Map<String, Long> resultado(Pauta pauta) {

        Collection<Voto> votos = sessaoRepository.findByPauta(pauta).isPresent() ? sessaoRepository.findByPauta(pauta).get().getVotos() : new ArrayList<>();

        Map<String, Long> result = new HashMap<>();
        result.put("SIM", votos.stream().filter(v -> v.getMensagemVoto().toString().equalsIgnoreCase("SIM")).count());
        result.put("NAO", votos.stream().filter(v -> v.getMensagemVoto().toString().equalsIgnoreCase("NAO")).count());

        return result;
    }
}
