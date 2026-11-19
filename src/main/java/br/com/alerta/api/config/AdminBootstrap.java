package br.com.alerta.api.config;

import br.com.alerta.api.domain.Perfil;
import br.com.alerta.api.domain.Usuario;
import br.com.alerta.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.senha}")
    private String adminSenha;

    @Value("${admin.nome}")
    private String adminNome;

    public AdminBootstrap(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!usuarioRepository.existsByEmailIgnoreCase(adminEmail)) {
            usuarioRepository.save(Usuario.builder()
                    .nome(adminNome)
                    .email(adminEmail)
                    .senhaHash(passwordEncoder.encode(adminSenha))
                    .perfil(Perfil.GESTOR)
                    .build());
        }
    }
}
