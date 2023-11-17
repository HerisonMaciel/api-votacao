package com.votacao.service;

import com.votacao.entity.Pauta;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @InjectMocks
    PautaService pautaService;

    @Mock
    PautaRepository pautaRepository;

    public Pauta pauta;

    @BeforeEach
    public void setUp(){
        pauta = new Pauta(01 , "pauta01");
    }

    @Test
    void AdicionarPauta(){
        when(pautaRepository.save(pauta))
                .thenReturn(pauta);

        Pauta pautaSave = pautaService.adicionarPauta(pauta);

        assertEquals(pauta, pautaSave);
        verify(pautaRepository).save(pauta);
        verifyNoMoreInteractions(pautaRepository);
    }

    @Test
    void BuscarPautaComSucesso(){
        when(pautaRepository.findById(pauta.getId()))
                .thenReturn(java.util.Optional.ofNullable(pauta));

        Optional<Pauta> getPauta = pautaService.getPauta(pauta.getId());

        assertEquals(java.util.Optional.ofNullable(pauta), getPauta);
        verify(pautaRepository).findById(pauta.getId());
        verifyNoMoreInteractions(pautaRepository);
    }

    @Test
    void BuscarPautaSemRegistro(){
        when(pautaRepository.findById(100))
                .thenThrow(new RuntimeException("Pauta não encontrada!"));

        final RuntimeException e = assertThrows(RuntimeException.class, () -> {
            pautaService.getPauta(100);
        });

        assertThat(e.getMessage(), is("Pauta não encontrada!"));
        verify(pautaRepository).findById(100);
        verifyNoMoreInteractions(pautaRepository);

    }

}
