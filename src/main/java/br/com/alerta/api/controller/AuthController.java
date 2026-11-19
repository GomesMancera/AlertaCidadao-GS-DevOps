package br.com.alerta.api.controller;

import br.com.alerta.api.dto.AuthDtos;
import br.com.alerta.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    public AuthDtos.TokenResponse registrar(@Valid @RequestBody AuthDtos.RegistrarRequest request) {
        return authService.registrar(request);
    }

    @PostMapping("/login")
    public AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.LoginRequest request) {
        return authService.login(request);
    }
}
