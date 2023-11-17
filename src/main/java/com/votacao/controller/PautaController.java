package com.votacao.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.dtos.request.PautaRequestDto;
import com.votacao.dtos.response.PautaResponseDto;
import com.votacao.entity.Pauta;
import com.votacao.entity.Voto;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.PautaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/v1/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<PautaResponseDto> criarPauta(@RequestBody @Valid PautaRequestDto pautaRequest) {
        log.info("Criando pauta... " + pautaRequest.toString());
        Pauta pauta = objectMapper.convertValue(pautaRequest, Pauta.class);
        pauta = pautaService.adicionarPauta(pauta);
        log.info("Pauta criada com sucesso!");
        return ResponseEntity.ok(objectMapper.convertValue(pauta, PautaResponseDto.class))
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PautaResponseDto> getPauta(@PathVariable Integer id) {

        try {
            Pauta pauta = pautaService.getPauta(id).get();
            PautaResponseDto pautaResponse = objectMapper.convertValue(pauta, PautaResponseDto.class);
            pautaResponse.setResultado(pautaService.resultado(pauta));
            return ResponseEntity.ok().body(pautaResponse);
        }catch (ExceptionVotacao e){
            log.info("Pauta não encontrada! Erro: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }


    }


    @GetMapping(value = "/pautas-resolvidas")
    public List<PautaResponseDto> getPautasComResultado() {
        log.info("Consultando pautas...");
        return pautaService.getPautas().stream()
                .map(this::getPautaResponse)
                .collect(Collectors.toList());
    }

    private PautaResponseDto getPautaResponse(Pauta pauta) {
        PautaResponseDto pautaResponse = objectMapper.convertValue(pauta, PautaResponseDto.class);
        pautaResponse.setResultado(pautaService.resultado(pauta));

        return pautaResponse;
    }

}
