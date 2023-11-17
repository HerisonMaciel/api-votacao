package com.votacao.service;

import com.votacao.entity.Pauta;
import com.votacao.entity.Sessao;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessaoService {

    private static Integer TEMPOPADRAO = 60;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaService pautaService;

    public void iniciarSessao(Integer idPauta, LocalDateTime dataFechamento) {
       Optional<Pauta> pautaOptional = pautaService.getPauta(idPauta);
       Pauta pauta = pautaOptional.get();
       criaSessao(pauta, dataFechamento.plusSeconds(TEMPOPADRAO));
    }

    private void criaSessao(Pauta pauta, LocalDateTime dataFechamento) {
        Sessao sessaoVotacao = Sessao.builder()
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(dataFechamento)
                .pauta(pauta)
                .build();

        sessaoRepository.save(sessaoVotacao);
    }

    public Integer getTempoPadrao() {
        return TEMPOPADRAO;
    }

}
