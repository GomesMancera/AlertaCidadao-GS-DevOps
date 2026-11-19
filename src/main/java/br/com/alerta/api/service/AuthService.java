package br.com.alerta.api.service;

import br.com.alerta.api.domain.Perfil;
import br.com.alerta.api.domain.Usuario;
import br.com.alerta.api.dto.AuthDtos;
import br.com.alerta.api.repository.UsuarioRepository;
import br.com.alerta.api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthDtos.TokenResponse registrar(AuthDtos.RegistrarRequest request) {
        if (usuarioRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail ja cadastrado");
        }
        Usuario usuario = usuarioRepository.save(Usuario.builder()
                .nome(request.nome())
                .email(request.email().toLowerCase())
                .senhaHash(passwordEncoder.encode(request.senha()))
                .perfil(Perfil.CIDADAO)
                .build());
        return tokens(usuario);
    }

    public AuthDtos.TokenResponse login(AuthDtos.LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas"));
        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas");
        }
        return tokens(usuario);
    }

    private AuthDtos.TokenResponse tokens(Usuario usuario) {
        return new AuthDtos.TokenResponse(
                jwtService.accessToken(usuario.getEmail(), usuario.getPerfil()),
                jwtService.refreshToken(usuario.getEmail(), usuario.getPerfil()),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }
}
