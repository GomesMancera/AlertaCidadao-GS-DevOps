package br.com.alerta.api.service;

import br.com.alerta.api.domain.Alerta;
import br.com.alerta.api.domain.Atualizacao;
import br.com.alerta.api.domain.Perfil;
import br.com.alerta.api.domain.Usuario;
import br.com.alerta.api.dto.AtualizacaoDtos;
import br.com.alerta.api.repository.AtualizacaoRepository;
import br.com.alerta.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AtualizacaoService {

    private final AtualizacaoRepository atualizacaoRepository;
    private final AlertaService alertaService;
    private final UsuarioRepository usuarioRepository;

    public AtualizacaoService(
            AtualizacaoRepository atualizacaoRepository,
            AlertaService alertaService,
            UsuarioRepository usuarioRepository
    ) {
        this.atualizacaoRepository = atualizacaoRepository;
        this.alertaService = alertaService;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AtualizacaoDtos.Response create(AtualizacaoDtos.CreateRequest request) {
        Alerta alerta = alertaService.loadVisible(request.alertaId());
        Usuario autor = currentUser();
        Atualizacao atualizacao = atualizacaoRepository.save(Atualizacao.builder()
                .alerta(alerta)
                .mensagem(request.mensagem())
                .statusNovo(request.statusNovo())
                .autor(autor)
                .build());
        alerta.setStatus(request.statusNovo());
        return toResponse(atualizacao);
    }

    @Transactional(readOnly = true)
    public List<AtualizacaoDtos.Response> listByAlerta(Long alertaId) {
        alertaService.loadVisible(alertaId);
        return atualizacaoRepository.findByAlertaIdOrderByCriadoEmDesc(alertaId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtualizacaoDtos.Response> listAll() {
        Usuario user = currentUser();
        if (user.getPerfil() == Perfil.GESTOR) {
            return atualizacaoRepository.findAll().stream().map(this::toResponse).toList();
        }
        return atualizacaoRepository.findAll().stream()
                .filter(a -> a.getAlerta().getCidadao().getId().equals(user.getId()))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AtualizacaoDtos.Response get(Long id) {
        return toResponse(loadOwned(id));
    }

    @Transactional
    public AtualizacaoDtos.Response update(Long id, AtualizacaoDtos.UpdateRequest request) {
        Atualizacao atualizacao = loadOwned(id);
        atualizacao.setMensagem(request.mensagem());
        atualizacao.setStatusNovo(request.statusNovo());
        atualizacao.getAlerta().setStatus(request.statusNovo());
        return toResponse(atualizacao);
    }

    @Transactional
    public void delete(Long id) {
        atualizacaoRepository.delete(loadOwned(id));
    }

    private Atualizacao loadOwned(Long id) {
        Atualizacao atualizacao = atualizacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atualizacao nao encontrada"));
        alertaService.loadVisible(atualizacao.getAlerta().getId());
        return atualizacao;
    }

    private Usuario currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao encontrado"));
    }

    private AtualizacaoDtos.Response toResponse(Atualizacao atualizacao) {
        return new AtualizacaoDtos.Response(
                atualizacao.getId(),
                atualizacao.getAlerta().getId(),
                atualizacao.getMensagem(),
                atualizacao.getStatusNovo(),
                atualizacao.getAutor().getId(),
                atualizacao.getAutor().getNome(),
                atualizacao.getCriadoEm()
        );
    }
}
