package com.votacao.service;

import com.votacao.entity.Pauta;
import com.votacao.entity.Sessao;
import com.votacao.enuns.MensagemException;
import com.votacao.exception.ExceptionVotacao;
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

    public Integer getTempoPadrao() {
        return TEMPOPADRAO;
    }

    public Optional<Sessao> getSessao(Pauta pauta) {
        return sessaoRepository.findByPauta(pauta);
    }

    public void iniciarSessao(Integer idPauta, Integer tempoSeg) {
       Pauta pauta = pautaService.getPauta(idPauta).get();

        LocalDateTime dataFechamento = LocalDateTime.now().plusSeconds(tempoSeg);

        if(Objects.requireNonNull(getSessao(pauta)).isPresent()){
            throw new ExceptionVotacao(MensagemException.SESSAO_JA_EXISTE, HttpStatus.CONFLICT);
        }

       criaSessao(pauta, dataFechamento);
    }

    private void criaSessao(Pauta pauta, LocalDateTime dataFechamento) {
        Sessao sessaoVotacao = Sessao.builder()
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(dataFechamento(dataFechamento))
                .pauta(pauta)
                .build();

        sessaoRepository.save(sessaoVotacao);
    }

    private LocalDateTime dataFechamento(LocalDateTime dataFechamento) {
        return dataFechamento == null ? LocalDateTime.now().plusSeconds(TEMPOPADRAO) : dataFechamento;
    }

}
