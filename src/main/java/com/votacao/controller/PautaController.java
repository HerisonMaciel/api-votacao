package com.votacao.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.dtos.request.PautaRequestDto;
import com.votacao.dtos.response.PautaResponseDto;
import com.votacao.entity.Pauta;
import com.votacao.service.PautaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
