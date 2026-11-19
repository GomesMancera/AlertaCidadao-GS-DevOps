# Folha de rosto — Global Solution 2026/2

## AlertaCidadao · DevOps Tools & Cloud Computing

| Campo | Valor |
|-------|-------|
| Grupo | Solo (DP) — **AlertaCidadao** |
| Nome completo | Gabriel Gomes Mancera |
| RM (representante) | 555427 |
| E-mail FIAP | RM555427@fiap.com.br |
| Disciplina | DevOps Tools & Cloud Computing |
| Tema CitizenTech | **AlertaCidadao** — alerta comunitário de alagamento (ODS 11 e 13) |

### Proposta

- **Cidadão:** morador do bairro  
- **Problema:** falta de canal rápido para reportar alagamento e acompanhar a resposta  
- **Solução:** API Java em nuvem (`ac_alerta` + `ac_atualizacao`) com JWT, Azure SQL e CI/CD no Azure DevOps  

---

## Links obrigatórios (rubrica)

1. **Organização Azure DevOps:** https://dev.azure.com/RM555427  
2. **Vídeo YouTube:** https://youtu.be/zi-IfYD7Q5I  

## Links complementares

3. **Projeto:** https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps  
4. **Repositório:** https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps  
5. **Aplicação AlertaCidadao:** https://app-alerta-gs555427.azurewebsites.net  
6. **Health:** https://app-alerta-gs555427.azurewebsites.net/health  

## Recursos Azure (AlertaCidadao)

| Recurso | Nome |
|---------|------|
| Resource Group | `rg-alerta-gs-devops` |
| App Service | `app-alerta-gs555427` |
| SQL Server | `sql-alerta-gs555427` |
| Database | `alertacidadao` |

## Observações DevOps

- Projeto privado: Boards + Repos + Pipelines  
- `main` protegida (revisor + Work Item)  
- CI/CD: `azure-pipeline.yml` + agent self-hosted  
- Professor: Org **Basic** + **Project Administrators**  
- Task **#12** Done · PR **#15** · Pipeline **#28**  

Arquivo de entrega: `GrupoSolo_rm555427.pdf`  
Prazo Portal FIAP: **19/11/2026 23h55**
