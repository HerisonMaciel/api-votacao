package com.votacao.entity;

import com.votacao.enuns.MensagemVoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "sessao")
@Entity
@Table(name = "voto")
public class Voto implements Serializable {

    @NotNull(message = "CPF obrigatório.")
    @Id
    private String cpfEleitor;

    @NotNull(message = "Voto é obrigatório e precisa seguir o padrão: SIM/NAO")
    @Column(name = "mensagem_voto")
    @Enumerated(EnumType.STRING)
    private MensagemVoto mensagemVoto;

    @Column(name = "data")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

}
