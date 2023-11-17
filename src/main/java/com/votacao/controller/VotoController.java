package com.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.dtos.request.VotoRequestDto;
import com.votacao.entity.Voto;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.VotoService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/v1/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/{idPauta}/votar")
    public ResponseEntity votar(@PathVariable("idPauta") Integer idPauta, @RequestBody @Valid VotoRequestDto voto) {
        try {
            votoService.votar(idPauta, objectMapper.convertValue(voto, Voto.class));
            log.info("Voto registrado com sucesso!");
        }catch (ExceptionVotacao e){
            log.info("Voto não registrado! Erro: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().build();
    }

}
