package br.com.alerta.api.dto;

import br.com.alerta.api.domain.StatusAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public final class AtualizacaoDtos {
    private AtualizacaoDtos() {}

    public record CreateRequest(
            @NotNull Long alertaId,
            @NotBlank @Size(max = 500) String mensagem,
            @NotNull StatusAlerta statusNovo
    ) {}

    public record UpdateRequest(
            @NotBlank @Size(max = 500) String mensagem,
            @NotNull StatusAlerta statusNovo
    ) {}

    public record Response(
            Long id,
            Long alertaId,
            String mensagem,
            StatusAlerta statusNovo,
            Long autorId,
            String autorNome,
            LocalDateTime criadoEm
    ) {}
}
