package com.votacao.service;

import com.votacao.entity.Pauta;
import com.votacao.entity.Sessao;
import com.votacao.entity.Voto;
import com.votacao.enuns.MensagemVoto;
import com.votacao.repository.PautaRepository;
import com.votacao.repository.SessaoRepository;
import com.votacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    public Voto voto;

    @Mock
    public Sessao s;



    @BeforeEach
    public void setUp(){
        voto = new Voto("503.815.680-08", MensagemVoto.SIM,
                LocalDateTime.now(), s);
    }

    @Test
    void VotarComSucesso(){

        Pauta pauta = new Pauta(01, "pauta01");
        Sessao sessao = new Sessao(01,LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(300), pauta, new LinkedHashSet<Voto>());

        when(pautaRepository.findById(pauta.getId())).thenReturn(java.util.Optional.of(pauta));
        when(sessaoRepository.findByPauta(pauta)).thenReturn(java.util.Optional.of(sessao));
        when(votoRepository.existsBySessaoAndCpfEleitor(any(Sessao.class), anyString())).thenReturn(false);


        votoService.votar(pauta.getId(), voto);

        verify(pautaRepository).findById(pauta.getId());
        verify(sessaoRepository).findByPauta(pauta);
        verify(votoRepository).existsBySessaoAndCpfEleitor(sessao, voto.getCpfEleitor());
        verify(votoRepository).save(voto);
        verifyNoMoreInteractions(votoRepository);
        verifyNoMoreInteractions(sessaoRepository);
        verifyNoMoreInteractions(pautaRepository);
    }

}
