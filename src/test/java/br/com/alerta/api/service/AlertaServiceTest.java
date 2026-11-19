package br.com.alerta.api.service;

import br.com.alerta.api.domain.*;
import br.com.alerta.api.dto.AlertaDtos;
import br.com.alerta.api.repository.AlertaRepository;
import br.com.alerta.api.repository.AtualizacaoRepository;
import br.com.alerta.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock AlertaRepository alertaRepository;
    @Mock AtualizacaoRepository atualizacaoRepository;
    @Mock UsuarioRepository usuarioRepository;
    @InjectMocks AlertaService alertaService;

    Usuario cidadao;

    @BeforeEach
    void setUp() {
        cidadao = Usuario.builder().id(1L).nome("Ana").email("ana@alerta.local").perfil(Perfil.CIDADAO).build();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("ana@alerta.local", null, List.of())
        );
        when(usuarioRepository.findByEmailIgnoreCase("ana@alerta.local")).thenReturn(Optional.of(cidadao));
    }

    @Test
    void createAlertaAbreComStatusAberto() {
        when(alertaRepository.save(any(Alerta.class))).thenAnswer(inv -> {
            Alerta a = inv.getArgument(0);
            a.setId(10L);
            return a;
        });

        var response = alertaService.create(new AlertaDtos.CreateRequest(
                "Centro", "Rua alagada", NivelRisco.ALTO
        ));

        assertEquals(10L, response.id());
        assertEquals(StatusAlerta.ABERTO, response.status());
        assertEquals("Centro", response.bairro());
    }
}
