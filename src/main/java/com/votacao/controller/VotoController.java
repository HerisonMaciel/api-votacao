package com.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.dtos.request.VotoRequestDto;
import com.votacao.entity.Voto;
import com.votacao.exception.ExceptionVotacao;
import com.votacao.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Operation(summary = "Realizar votação", description = "Realizar votação", tags = {"Voto"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Voto.class)))),
            @ApiResponse(responseCode = "400", description = "Request invalid!"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    @PostMapping("/{idPauta}/votar")
    public ResponseEntity votar(@PathVariable("idPauta") Integer idPauta, @RequestBody @Valid VotoRequestDto voto) {
        try {
            votoService.votar(idPauta, objectMapper.convertValue(voto, Voto.class));
            log.info("Voto registrado com sucesso!");
        }catch (ExceptionVotacao e){
            log.info("Voto não registrado! Erro: " + e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
