package com.votacao.service;

import com.votacao.dtos.request.SessaoRequestDto;
import com.votacao.entity.Pauta;
import com.votacao.entity.Sessao;
import com.votacao.enuns.MensagemException;
import com.votacao.exception.ExceptionVotacao;
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
    private PautaRepository pautaRepository;

    public Integer getTempo() {
        return TEMPOPADRAO;
    }

    public Optional<Sessao> getSessao(Pauta pauta) {
        return sessaoRepository.findByPauta(pauta);
    }

    public void iniciarSessao(SessaoRequestDto sessaoRequestDto) {
       Pauta pauta = pautaRepository.getReferenceById(sessaoRequestDto.getIdPauta());

       LocalDateTime dataFechamento = LocalDateTime.now().plusSeconds(TEMPOPADRAO);

       if(sessaoRequestDto.getTempoSegundo() != null){
           dataFechamento = LocalDateTime.now().plusSeconds(sessaoRequestDto.getTempoSegundo());
       }

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
