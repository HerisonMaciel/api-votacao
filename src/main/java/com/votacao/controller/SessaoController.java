package com.votacao.controller;

import com.votacao.dtos.request.SessaoRequestDto;
import com.votacao.dtos.request.VotoRequestDto;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.SessaoService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Log4j2
@RequestMapping("/v1/sessao")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/iniciar-sessao")
    public ResponseEntity iniciarSessao(@RequestBody SessaoRequestDto sessaoRequestDto) {

        try{
            log.info("Iniciando sessão de votação...", sessaoRequestDto.getIdPauta());
            sessaoService.iniciarSessao(sessaoRequestDto);
            if(sessaoRequestDto.getTempoSegundo() == null)
                log.info("Sessão de votação iniciada com sucesso, o tempo de votação encerra em " + sessaoService.getTempo() + " segundos.");
            else log.info("Sessão de votação iniciada com sucesso, o tempo de votação encerra em " + sessaoRequestDto.getTempoSegundo() + " segundos.");
        }catch (ExceptionVotacao e){
            log.info("Sessão não iniciada! Erro: " + e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

}
