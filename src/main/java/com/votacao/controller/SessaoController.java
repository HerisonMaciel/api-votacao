package com.votacao.controller;

import com.votacao.dtos.request.SessaoRequestDto;
import com.votacao.entity.Sessao;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@RequestMapping("/v1/sessao")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @Operation(summary = "Iniciar sessão", description = "Iniciar sessão", tags = {"Sessão"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sessao.class)))),
            @ApiResponse(responseCode = "400", description = "Request invalid!"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
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
