package com.votacao.service;

import com.votacao.entity.Sessao;
import com.votacao.entity.Voto;
import com.votacao.enuns.MensagemVoto;
import com.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private PautaService pautaService;

    public void votar(Integer idPauta, Voto voto) {

        Sessao sessao = sessaoService.getSessao(pautaService.getPauta(idPauta).get()).get();

        voto.setSessao(sessao);
        voto.setDataHora(LocalDateTime.now());

        if(!votoRepository.existsBySessaoAndCpfEleitor(sessao, voto.getCpfEleitor())) {
            System.out.println( " VOTO: " + voto.toString());
            votoRepository.save(voto);
        }

    }

}
