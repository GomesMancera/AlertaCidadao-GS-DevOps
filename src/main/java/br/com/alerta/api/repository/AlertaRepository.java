package br.com.alerta.api.repository;

import br.com.alerta.api.domain.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByCidadaoIdOrderByCriadoEmDesc(Long cidadaoId);
    List<Alerta> findAllByOrderByCriadoEmDesc();
}
