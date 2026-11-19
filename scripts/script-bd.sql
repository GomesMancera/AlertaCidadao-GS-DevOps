-- DDL AlertaCidadao (Azure SQL) — espelho do Flyway V1
-- Tabelas de negocio: ac_alerta, ac_atualizacao (+ ac_usuario auth)

IF OBJECT_ID('ac_atualizacao', 'U') IS NOT NULL DROP TABLE ac_atualizacao;
IF OBJECT_ID('ac_alerta', 'U') IS NOT NULL DROP TABLE ac_alerta;
IF OBJECT_ID('ac_usuario', 'U') IS NOT NULL DROP TABLE ac_usuario;

CREATE TABLE ac_usuario (
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome          NVARCHAR(120) NOT NULL,
    email         NVARCHAR(180) NOT NULL UNIQUE,
    senha_hash    NVARCHAR(255) NOT NULL,
    perfil        NVARCHAR(20)  NOT NULL,
    criado_em     DATETIME2     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ac_alerta (
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    bairro        NVARCHAR(120) NOT NULL,
    descricao     NVARCHAR(500) NOT NULL,
    nivel_risco   NVARCHAR(20)  NOT NULL,
    status        NVARCHAR(30)  NOT NULL,
    cidadao_id    BIGINT        NOT NULL,
    criado_em     DATETIME2     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alerta_cidadao FOREIGN KEY (cidadao_id) REFERENCES ac_usuario(id)
);

CREATE TABLE ac_atualizacao (
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    alerta_id     BIGINT        NOT NULL,
    mensagem      NVARCHAR(500) NOT NULL,
    status_novo   NVARCHAR(30)  NOT NULL,
    autor_id      BIGINT        NOT NULL,
    criado_em     DATETIME2     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_atualizacao_alerta FOREIGN KEY (alerta_id) REFERENCES ac_alerta(id),
    CONSTRAINT fk_atualizacao_autor FOREIGN KEY (autor_id) REFERENCES ac_usuario(id)
);

CREATE INDEX ix_alerta_bairro ON ac_alerta(bairro);
CREATE INDEX ix_atualizacao_alerta ON ac_atualizacao(alerta_id);
