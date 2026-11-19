package br.com.alerta.api.dto;

import br.com.alerta.api.domain.NivelRisco;
import br.com.alerta.api.domain.StatusAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public final class AlertaDtos {
    private AlertaDtos() {}

    public record CreateRequest(
            @NotBlank @Size(max = 120) String bairro,
            @NotBlank @Size(max = 500) String descricao,
            @NotNull NivelRisco nivelRisco
    ) {}

    public record UpdateRequest(
            @NotBlank @Size(max = 120) String bairro,
            @NotBlank @Size(max = 500) String descricao,
            @NotNull NivelRisco nivelRisco,
            @NotNull StatusAlerta status
    ) {}

    public record Response(
            Long id,
            String bairro,
            String descricao,
            NivelRisco nivelRisco,
            StatusAlerta status,
            Long cidadaoId,
            String cidadaoNome,
            LocalDateTime criadoEm
    ) {}
}
