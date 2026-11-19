package br.com.alerta.api.dto;

import br.com.alerta.api.domain.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDtos {
    private AuthDtos() {}

    public record RegistrarRequest(
            @NotBlank @Size(max = 120) String nome,
            @NotBlank @Email String email,
            @NotBlank @Size(min = 6, max = 80) String senha
    ) {}

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String senha
    ) {}

    public record TokenResponse(
            String token,
            String refreshToken,
            String nome,
            String email,
            Perfil perfil
    ) {}
}
