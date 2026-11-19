package br.com.alerta.api.repository;

import br.com.alerta.api.domain.Atualizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtualizacaoRepository extends JpaRepository<Atualizacao, Long> {
    List<Atualizacao> findByAlertaIdOrderByCriadoEmDesc(Long alertaId);
}
