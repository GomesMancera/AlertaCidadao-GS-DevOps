# AlertaCidadao — CitizenTech

**Global Solution 2026/2 · DevOps Tools & Cloud Computing**  
Gabriel Gomes Mancera · RM **555427**

API Java em nuvem para o cidadão reportar alagamento e acompanhar o atendimento — com Azure App Service, Azure SQL e ciclo completo no Azure DevOps.

---

## Proposta CitizenTech

| Critério GS | Resposta |
|-------------|---------|
| **Cidadão beneficiado** | Morador do bairro |
| **Problema** | Falta de canal rápido para reportar alagamento / risco hídrico e acompanhar a resposta |
| **Solução** | **AlertaCidadao**: API em nuvem — cidadão abre alerta; gestor/cidadão registra atualizações de status |
| **Como funciona** | JWT (`CIDADAO` / `GESTOR`) + CRUD em `ac_alerta` e `ac_atualizacao` + Azure SQL |
| **Ecossistema** | Cidadão + poder público/gestor + Azure App Service + Azure DevOps |
| **Impacto** | Alerta comunitário mais rápido; rastreio do atendimento |
| **ODS** | **11** Cidades e comunidades sustentáveis · **13** Ação contra a mudança climática |

---

## Links da entrega

| Recurso | URL |
|---------|-----|
| Azure DevOps (projeto) | https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps |
| Repositório (Azure Repos) | https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps |
| Aplicação / UI | https://app-alerta-gs555427.azurewebsites.net |
| Health | https://app-alerta-gs555427.azurewebsites.net/health |
| Vídeo (YouTube) | https://youtu.be/zi-IfYD7Q5I |
| Resource Group | `rg-alerta-gs-devops` |

---

## O que roda na nuvem

- **Front React** na raiz do App Service (`/`) — home CitizenTech + painel da API  
- **API Spring Boot** em `/api/**` (auth JWT, alertas, atualizações)  
- **Health** em `/health`  
- **Azure SQL** `alertacidadao` — tabelas `ac_alerta` e `ac_atualizacao` (CRUD completo C/R/U/D)

Fluxo DevOps: **Boards → branch → PR (revisor + work item) → Pipeline CI/CD → App Service → Azure SQL**.

---

## Arquitetura

[`docs/ARQUITETURA_MACRO.md`](docs/ARQUITETURA_MACRO.md)

## CRUD (exemplos JSON / REST Client)

- [`docs/crud-examples.json`](docs/crud-examples.json)
- [`docs/requisicoes.http`](docs/requisicoes.http)

Login demo (UI): `ana.souza@alerta.local` / `Senha@123`

## Scripts e pipeline

| Artefato | Caminho |
|----------|---------|
| Provisionamento Azure CLI | `scripts/provision-azure.ps1` · `.sh` |
| DDL / script de banco | `scripts/script-bd.sql` |
| Pipeline CI/CD | `azure-pipeline.yml` |
| Docker | `dockerfiles/Dockerfile` (+ `Dockerfile` na raiz) |
| Folha de rosto (entrega) | [`docs/GrupoSolo_rm555427.md`](docs/GrupoSolo_rm555427.md) |

## Stack

Java **21** · Spring Boot **3.3** · Flyway · JWT · React + Vite · Azure App Service (Linux) · Azure SQL · Azure Pipelines (agent self-hosted)

**Uso de IA:** apoio em scaffold, scripts, README e roteiro. Conteúdo revisado pelo aluno.
