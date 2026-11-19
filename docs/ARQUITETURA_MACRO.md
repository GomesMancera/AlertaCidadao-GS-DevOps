# Arquitetura Macro â€” AlertaCidadao (CitizenTech / DevOps GS)

## CitizenTech (resumo)
- **CidadÃ£o:** morador do bairro  
- **Problema:** alagamento / risco hÃ­drico sem canal de alerta  
- **SoluÃ§Ã£o:** API de alertas + atualizaÃ§Ãµes de status  
- **ODS 11** Â· **ODS 13**

## Diagrama

```mermaid
flowchart TB
  subgraph client [Cliente]
    webUi[Front_React_AlertaCidadao]
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
  webUi --> webapp
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
| Front | React + Vite (servido no mesmo App Service) |
| App | Spring Boot 3.3 / Java 21 / JWT |
| PersistÃªncia | JPA + Flyway + Azure SQL (`ac_*`) |
| CI/CD | Azure Pipelines (`azure-pipeline.yml`) |
| IaC | `scripts/provision-azure.ps1` |

