package com.votacao.service;

import com.votacao.dtos.request.SessaoRequestDto;
import com.votacao.entity.Pauta;
import com.votacao.entity.Sessao;
import com.votacao.entity.Voto;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @InjectMocks
    SessaoService sessaoService;

    @Mock
    SessaoRepository sessaoRepository;

    @Mock
    PautaRepository pautaRepository;

    public Sessao sessao;

    public SessaoRequestDto sessaoRequestDto;

    @Mock
    public Pauta pauta;

    @Mock
    public Collection<Voto> votos;

    @BeforeEach
    public void setUp(){
        /*pauta = new Pauta(01 , "pauta01");
        votos = new ArrayList<Voto>((Collection<? extends Voto>) new Voto("898.440.550-75",
                MensagemVoto.SIM, LocalDateTime.now(), sessao));*/
        sessao = new Sessao(01, LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(60), pauta, votos);
        sessaoRequestDto = new SessaoRequestDto(pauta.getId(), 300);
    }

    @Test
    void IniciarSessaoComSucesso(){
        when(pautaRepository.getReferenceById(pauta.getId()))
                .thenReturn(pauta);

        sessaoService.iniciarSessao(sessaoRequestDto);

        verify(pautaRepository).getReferenceById(pauta.getId());
        verifyNoMoreInteractions(pautaRepository);
    }

    @Test
    void IniciarSessaoJaExistente(){
        when(sessaoRepository.findByPauta(pauta))
                .thenThrow(new RuntimeException("Sessão já cadastrada com este ID!"));

        final RuntimeException e = assertThrows(RuntimeException.class, () -> {
            sessaoService.getSessao(pauta);
        });

        assertThat(e.getMessage(), is("Sessão já cadastrada com este ID!"));
        verify(sessaoRepository).findByPauta(pauta);
        verifyNoMoreInteractions(sessaoRepository);
    }

}
