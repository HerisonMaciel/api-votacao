package com.votacao.dtos.request;

import com.votacao.enuns.MensagemVoto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class VotoRequestDto {

    @NotNull(message = "CPF é obrigatório.")
    private String cpfEleitor;

    @NotNull(message = "Voto é obrigatório e precisa seguir o padrão: SIM/NAO")
    private MensagemVoto mensagemVoto;

}
