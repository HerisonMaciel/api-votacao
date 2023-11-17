package com.votacao.controller;

import com.votacao.service.SessaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{idPauta}/iniciar-sessao")
    public ResponseEntity iniciarSessaoVotacao(@PathVariable("idPauta") Integer idPauta) {
        log.info("Iniciando sessão de votação...", idPauta);
        sessaoService.iniciarSessao(idPauta, LocalDateTime.now());
        log.info("Sessão de votação iniciada com sucesso, o tempo de votação encerra em " + sessaoService.getTempoPadrao() + " segundos.");

        return ResponseEntity.ok().build();
    }

}
