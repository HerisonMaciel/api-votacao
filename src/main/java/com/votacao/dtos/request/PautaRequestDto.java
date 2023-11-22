package com.votacao.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class PautaRequestDto {
    @NotBlank(message = "Nome obrigatório")
    private String nome;
}
