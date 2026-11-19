# AlertaCidadao — CitizenTech (DevOps GS 2026/2)

## Proposta CitizenTech

| Critério GS | Resposta |
|-------------|---------|
| **Cidadão beneficiado** | Morador do bairro |
| **Problema** | Falta de canal rápido para reportar alagamento/risco hídrico e acompanhar a resposta |
| **Solução** | API em nuvem: cidadão abre alerta; gestor/cidadão registra atualizações de status |
| **Como funciona** | JWT (CIDADAO/GESTOR) + CRUD em `ac_alerta` e `ac_atualizacao` + Azure SQL |
| **Ecossistema** | Cidadão + poder público/gestor + Azure App Service + Azure DevOps |
| **Impacto** | Alerta comunitário mais rápido; rastreio do atendimento |
| **ODS** | **11** Cidades e comunidades sustentáveis · **13** Ação contra a mudança climática |

| | |
|---|---|
| Aluno | Gabriel Gomes Mancera (RM 555427) |
| Azure DevOps | https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps |
| App / UI | https://app-alerta-gs555427.azurewebsites.net |
| Health | https://app-alerta-gs555427.azurewebsites.net/health |
| Resource Group | `rg-alerta-gs-devops` |

Fluxo DevOps: **Boards → branch → PR → Pipeline CI/CD → App Service → Azure SQL**.  
A raiz do App Service serve o front React; a API fica em `/api/**` e `/health`.

## Arquitetura macro
[`docs/ARQUITETURA_MACRO.md`](docs/ARQUITETURA_MACRO.md)

## CRUD em JSON
- [`docs/crud-examples.json`](docs/crud-examples.json)
- [`docs/requisicoes.http`](docs/requisicoes.http)

## Scripts / pipeline
- `scripts/provision-azure.ps1` / `.sh`
- `scripts/script-bd.sql`
- `azure-pipeline.yml`
- `dockerfiles/Dockerfile`

**Uso de IA:** apoio em scaffold, scripts, README e roteiro. Revisado pelo aluno.
