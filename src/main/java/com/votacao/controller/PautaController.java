package com.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.dtos.request.PautaRequestDto;
import com.votacao.dtos.response.PautaResponseDto;
import com.votacao.entity.Pauta;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @Operation(summary = "Criação da Pauta", description = "Criação da Pauta", tags = {"Pauta"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PautaResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Request invalid!"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
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

    @Operation(summary = "Busca da Pauta", description = "Buscar Pauta", tags = {"Pauta"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PautaResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Request invalid!"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
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

    @Operation(summary = "Buscar todas as pautas resolvidas", description = "Buscar todas as pautas resolvidas", tags = {"Pauta"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PautaResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Request invalid!"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
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
