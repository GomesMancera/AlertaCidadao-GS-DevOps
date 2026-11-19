# Arquitetura Macro — AlertaCidadao (CitizenTech / DevOps GS)

## CitizenTech (resumo)
- **Cidadão:** morador do bairro  
- **Problema:** alagamento / risco hídrico sem canal de alerta  
- **Solução:** API de alertas + atualizações de status  
- **ODS 11** · **ODS 13**

## Diagrama

```mermaid
flowchart TB
  subgraph client [Cliente]
    apiClient[API_REST_JSON]
  end
  subgraph ado [Azure_DevOps]
    boards[Azure_Boards]
    repos[Azure_Repos]
    ci[Pipeline_Build_CI]
    cd[Pipeline_Release_CD]
  end
  subgraph azure [Azure_Cloud]
    webapp[App_Service_Java21]
    sql[Azure_SQL_Database]
  end
  apiClient --> webapp
  repos --> ci
  ci -->|artefatos_JAR_testes| cd
  cd --> webapp
  webapp --> sql
  boards -.->|WI_links| repos
  scripts[scripts_Azure_CLI] --> webapp
  scripts --> sql
```

## Componentes
| Camada | Tecnologia |
|--------|------------|
| App | Spring Boot 3.3 / Java 21 / JWT |
| Persistência | JPA + Flyway + Azure SQL (`ac_*`) |
| CI/CD | Azure Pipelines (`azure-pipeline.yml`) |
| IaC | `scripts/provision-azure.ps1` |
