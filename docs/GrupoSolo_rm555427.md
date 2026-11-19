# Folha de rosto — Global Solution 2026/2

## AlertaCidadao · DevOps Tools & Cloud Computing

| Campo | Valor |
|-------|-------|
| Grupo | Solo (DP) |
| Nome | Gabriel Gomes Mancera |
| RM | 555427 |
| E-mail FIAP | RM555427@fiap.com.br |
| Disciplina | DevOps Tools & Cloud Computing |
| Tema CitizenTech | **AlertaCidadao** — alerta comunitário de alagamento (ODS 11 e 13) |

### Proposta (resumo)

- **Cidadão:** morador do bairro  
- **Problema:** falta de canal rápido para reportar alagamento e acompanhar a resposta  
- **Solução:** API Java em nuvem (`ac_alerta` + `ac_atualizacao`) com JWT, Azure SQL e CI/CD no Azure DevOps  

---

## Links obrigatórios

1. **Organização Azure DevOps:** https://dev.azure.com/RM555427  
2. **Projeto:** https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps  
3. **Repositório:** https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps  
4. **Aplicação em nuvem (AlertaCidadao):** https://app-alerta-gs555427.azurewebsites.net  
5. **Health:** https://app-alerta-gs555427.azurewebsites.net/health  
6. **Vídeo YouTube:** https://youtu.be/zi-IfYD7Q5I  

---

## Recursos Azure

| Recurso | Nome |
|---------|------|
| Resource Group | `rg-alerta-gs-devops` |
| App Service | `app-alerta-gs555427` |
| SQL Server | `sql-alerta-gs555427` |
| Database | `alertacidadao` |

## Observações DevOps

- Projeto privado: Boards + Repos + Pipelines  
- Branch `main` protegida (revisor obrigatório + vínculo de Work Item)  
- CI/CD: `azure-pipeline.yml` + agent self-hosted  
- Professor convidado: Org **Basic** + **Project Administrators**  
- Task de demonstração: **#12** (Done), PR **#15**, Pipeline **#28**  

Prazo Portal FIAP: **19/11/2026 23h55**
