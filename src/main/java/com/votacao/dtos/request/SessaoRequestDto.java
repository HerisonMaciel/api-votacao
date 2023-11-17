package com.votacao.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class SessaoRequestDto {
    @NotBlank(message = "Id pauta é obrigatório")
    private Integer idPauta;

    private Integer tempoSegundo;
}
