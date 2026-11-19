package br.com.alerta.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ac_atualizacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atualizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "alerta_id", nullable = false)
    private Alerta alerta;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_novo", nullable = false, length = 30)
    private StatusAlerta statusNovo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(name = "criado_em", nullable = false, columnDefinition = "datetime2")
    private LocalDateTime criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }
}
