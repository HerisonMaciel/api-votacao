package com.votacao.controller;

import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.SessaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Log4j2
@RequestMapping("/v1/sessao")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/{idPauta}/iniciar-sessao/{tempoSeg}")
    public ResponseEntity iniciarSessaoVotacao(@PathVariable("idPauta") Integer idPauta, @PathVariable("tempoSeg") Integer tempoSeg) {

        try{
            log.info("Iniciando sessão de votação...", idPauta);
            sessaoService.iniciarSessao(idPauta, tempoSeg);
            log.info("Sessão de votação iniciada com sucesso, o tempo de votação encerra em " + sessaoService.getTempoPadrao() + " segundos.");
        }catch (ExceptionVotacao e){
            log.info("Sessão não iniciada! Erro: " + e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }


        return ResponseEntity.ok().build();
    }

}
