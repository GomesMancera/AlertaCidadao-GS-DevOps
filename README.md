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

Alinha ao eixo **Clima e emergências** do documento CitizenTech.

| | |
|---|---|
| Aluno | Gabriel Gomes Mancera (RM 555427) |
| Azure DevOps | https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps |
| App | https://app-alerta-gs562279.azurewebsites.net |
| Health | https://app-alerta-gs562279.azurewebsites.net/health |
| Resource Group | `rg-petcare-gs-devops` (reutilizado; SP sem permissão de criar RG novo) |

Fluxo DevOps: **Boards → branch → PR → Pipeline CI/CD → App Service → Azure SQL**.

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
