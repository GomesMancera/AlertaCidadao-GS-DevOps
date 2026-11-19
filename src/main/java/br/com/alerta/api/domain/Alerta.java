package br.com.alerta.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ac_alerta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String bairro;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_risco", nullable = false, length = 20)
    private NivelRisco nivelRisco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAlerta status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cidadao_id", nullable = false)
    private Usuario cidadao;

    @Column(name = "criado_em", nullable = false, columnDefinition = "datetime2")
    private LocalDateTime criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusAlerta.ABERTO;
        }
    }
}
