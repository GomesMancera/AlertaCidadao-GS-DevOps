package br.com.alerta.api.service;

import br.com.alerta.api.domain.Alerta;
import br.com.alerta.api.domain.Perfil;
import br.com.alerta.api.domain.StatusAlerta;
import br.com.alerta.api.domain.Usuario;
import br.com.alerta.api.dto.AlertaDtos;
import br.com.alerta.api.repository.AlertaRepository;
import br.com.alerta.api.repository.AtualizacaoRepository;
import br.com.alerta.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final AtualizacaoRepository atualizacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AlertaService(
            AlertaRepository alertaRepository,
            AtualizacaoRepository atualizacaoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.alertaRepository = alertaRepository;
        this.atualizacaoRepository = atualizacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AlertaDtos.Response create(AlertaDtos.CreateRequest request) {
        Usuario cidadao = currentUser();
        Alerta alerta = alertaRepository.save(Alerta.builder()
                .bairro(request.bairro())
                .descricao(request.descricao())
                .nivelRisco(request.nivelRisco())
                .status(StatusAlerta.ABERTO)
                .cidadao(cidadao)
                .build());
        return toResponse(alerta);
    }

    @Transactional(readOnly = true)
    public List<AlertaDtos.Response> list() {
        Usuario user = currentUser();
        List<Alerta> alertas = user.getPerfil() == Perfil.GESTOR
                ? alertaRepository.findAllByOrderByCriadoEmDesc()
                : alertaRepository.findByCidadaoIdOrderByCriadoEmDesc(user.getId());
        return alertas.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlertaDtos.Response get(Long id) {
        return toResponse(loadVisible(id));
    }

    @Transactional
    public AlertaDtos.Response update(Long id, AlertaDtos.UpdateRequest request) {
        Alerta alerta = loadVisible(id);
        Usuario user = currentUser();
        if (user.getPerfil() != Perfil.GESTOR && !alerta.getCidadao().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissao");
        }
        alerta.setBairro(request.bairro());
        alerta.setDescricao(request.descricao());
        alerta.setNivelRisco(request.nivelRisco());
        alerta.setStatus(request.status());
        return toResponse(alerta);
    }

    @Transactional
    public void delete(Long id) {
        Alerta alerta = loadVisible(id);
        Usuario user = currentUser();
        if (user.getPerfil() != Perfil.GESTOR && !alerta.getCidadao().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissao");
        }
        atualizacaoRepository.findByAlertaIdOrderByCriadoEmDesc(alerta.getId())
                .forEach(atualizacaoRepository::delete);
        alertaRepository.delete(alerta);
    }

    public Alerta loadVisible(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta nao encontrado"));
        Usuario user = currentUser();
        if (user.getPerfil() != Perfil.GESTOR && !alerta.getCidadao().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissao");
        }
        return alerta;
    }

    private Usuario currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nao autenticado");
        }
        return usuarioRepository.findByEmailIgnoreCase(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao encontrado"));
    }

    private AlertaDtos.Response toResponse(Alerta alerta) {
        return new AlertaDtos.Response(
                alerta.getId(),
                alerta.getBairro(),
                alerta.getDescricao(),
                alerta.getNivelRisco(),
                alerta.getStatus(),
                alerta.getCidadao().getId(),
                alerta.getCidadao().getNome(),
                alerta.getCriadoEm()
        );
    }
}
