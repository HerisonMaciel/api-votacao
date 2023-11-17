package com.votacao.service;

import com.votacao.entity.Sessao;
import com.votacao.entity.Voto;
import com.votacao.enuns.MensagemException;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import com.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public void votar(Integer idPauta, Voto voto) {

        Sessao sessao = sessaoRepository.findByPauta(pautaRepository.findById(idPauta)
                .orElseThrow(() -> new ExceptionVotacao(MensagemException.PAUTA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND)))
                .orElseThrow(() -> new ExceptionVotacao(MensagemException.SESSAO_NAO_ENCONTRADA, HttpStatus.NOT_FOUND));

        if (LocalDateTime.now().isAfter(sessao.getDataFechamento())) {
            throw new ExceptionVotacao(MensagemException.SESSAO_FECHADA, HttpStatus.BAD_REQUEST);
        }

        voto.setSessao(sessao);
        voto.setDataHora(LocalDateTime.now());

        if(votoRepository.existsBySessaoAndCpfEleitor(sessao, voto.getCpfEleitor())) {
            throw new ExceptionVotacao(MensagemException.VOTO_JA_REGISTRADO, HttpStatus.BAD_REQUEST);
        }

        votoRepository.save(voto);

    }

}
