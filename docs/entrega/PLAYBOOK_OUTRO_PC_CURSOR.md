# PLAYBOOK — Outro PC / outro Cursor (apresentação AlertaCidadao)

**Leia este arquivo inteiro e execute agora.**  
Objetivo: deixar o notebook **100% pronto** para a apresentação presencial DevOps (10 min · 30 pts) do **Gabriel Gomes Mancera · RM 555427 · AlertaCidadao (Solo)**.

> Não é DigitalOcean. Stack = **Azure App Service + Azure SQL + Azure DevOps**.

---

## 0) O que o agente deve fazer (ordem)

1. Verificar se App/Health estão UP.  
2. Abrir **todas** as URLs no navegador padrão (Chrome/Edge).  
3. Orientar/autenticar onde precisar (ADO, Portal Azure, SQL Query Editor, painel do app).  
4. Deixar o **roteiro** visível (este arquivo + seção “Na apresentação”).  
5. Resumir ao Gabriel: “pronto — abas X, Y, Z; falta só você falar”.

**NÃO** apagar Resource Group. **NÃO** rodar teardown. **NÃO** mudar código agora.

---

## 1) Contas / autenticação

| Onde | Conta | Como |
|------|-------|------|
| Azure Portal | `RM555427@fiap.com.br` | Login Microsoft / FIAP |
| Azure DevOps | mesma conta (org `RM555427`) | https://dev.azure.com/RM555427 |
| App (painel) | `ana.souza@alerta.local` / `Senha@123` | Aba Entrar |
| SQL Query Editor | user `alertaadmin` | Aba **SQL authentication** (não Entra) |

### Senha SQL (Query Editor)

Buscar sem inventar (no terminal do PC, com `az` logado na assinatura FIAP):

```powershell
az webapp config appsettings list -g rg-alerta-gs-devops -n app-alerta-gs555427 --query "[?name=='DB_PASSWORD' || name=='DB_USER'].{name:name,value:value}" -o table
```

Digitar a senha **fora do enquadramento** do projetor se estiver gravando/projetando.

Professor já deve estar: Org **Basic** + projeto **Project Administrators** (Antonio Figueiredo).

---

## 2) Smoke check (obrigatório antes de abrir abas)

```powershell
Invoke-RestMethod "https://app-alerta-gs555427.azurewebsites.net/health" | ConvertTo-Json
# Esperado: status UP, service alerta-cidadao-api
try { (Invoke-WebRequest "https://app-alerta-gs555427.azurewebsites.net/" -UseBasicParsing).StatusCode } catch { $_ }
```

Se health falhar: `az webapp restart -g rg-alerta-gs-devops -n app-alerta-gs555427` e esperar ~30–60 s.

---

## 3) Abrir tudo no navegador (Windows)

Rodar no PowerShell (abre Edge/Chrome com as abas da apresentação):

```powershell
$urls = @(
  # App / demo
  "https://app-alerta-gs555427.azurewebsites.net/",
  "https://app-alerta-gs555427.azurewebsites.net/health",
  # Azure Portal
  "https://portal.azure.com/#@/resource/subscriptions/5babb7b4-556a-4ba7-ae4f-091626f08fed/resourceGroups/rg-alerta-gs-devops/overview",
  "https://portal.azure.com/#view/Microsoft_Azure_AppServiceEnvironmentVariables/AppsEnvironmentVariablesBlade/resourceId/%2Fsubscriptions%2F5babb7b4-556a-4ba7-ae4f-091626f08fed%2FresourceGroups%2Frg-alerta-gs-devops%2Fproviders%2FMicrosoft.Web%2Fsites%2Fapp-alerta-gs555427",
  "https://portal.azure.com/#@/resource/subscriptions/5babb7b4-556a-4ba7-ae4f-091626f08fed/resourceGroups/rg-alerta-gs-devops/providers/Microsoft.Sql/servers/sql-alerta-gs555427/databases/alertacidadao/queryEditor",
  # Azure DevOps
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_workitems/edit/12",
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps/pullrequests?_a=completed",
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_settings/repositories?_a=policiesMid&repositoryId=7eaf8a18-6d01-49d3-8ad6-de28f8cd328e&refs=refs/heads/main",
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_build?definitionId=2",
  # Docs / diagrama
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps?path=/docs/ARQUITETURA_MACRO.md",
  "https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_git/AlertaCidadao-GS-DevOps?path=/README.md",
  # Vídeo (backup)
  "https://youtu.be/zi-IfYD7Q5I"
)

foreach ($u in $urls) { Start-Process $u; Start-Sleep -Milliseconds 400 }
```

Se a URL de Environment variables / policies falhar, abrir manualmente:
- Portal → `app-alerta-gs555427` → **Settings → Environment variables** (só nomes, sem senha)
- ADO → Project Settings → Repositories → `AlertaCidadao-GS-DevOps` → Policies → `main`

### Abrir o roteiro no Cursor

Abrir este arquivo e/ou:
- `ENTREGA/ROTEIRO_PRESENCIAL.md` (se a pasta `GS-AlertaCidadao` estiver no PC)
- Senão, usar a seção **“4) Na apresentação”** abaixo (está completa).

---

## 4) Na apresentação — o que fazer / o que mostrar (10 min · 30 pts)

### Critérios do professor

| Pts | Critério | O que mostrar |
|----:|----------|----------------|
| 10 | App na nuvem | URL azurewebsites, CRUD, SELECT no SQL |
| 10 | Arquitetura | Diagrama + por que App Service / SQL / DevOps |
| 10 | Azure DevOps | Boards, Repos/PR/policies, Pipelines, vars |

### Ordem na hora (falar + clicar)

**0:00 Abertura (home do app)**  
> Sou Gabriel, RM 555427, projeto AlertaCidadao (CitizenTech): cidadão reporta alagamento. ODS 11 e 13. App no Azure, não localhost.

**0:40 Health**  
Mostrar `/health` UP.

**1:00 CRUD no painel**  
Entrar → Publicar alerta → listar → Atualizar → Atualizações → enviar → Excluir (C/R/U/D nas 2 tabelas `ac_alerta` e `ac_atualizacao`).

**3:30 SELECT**  
Query editor:

```sql
SELECT * FROM ac_alerta;
SELECT * FROM ac_atualizacao;
```

> Persistência no Azure SQL — prova com SELECT, não só GET.

**4:30 Arquitetura**  
Abrir `ARQUITETURA_MACRO.md`: Front React + API Java 21 no App Service → Azure SQL; Boards → branch → PR → Pipeline CI/CD → deploy.

**6:30 DevOps**  
1. Task **#12** Done + links  
2. PR completed + policies da `main` (revisor + work item)  
3. Pipeline verde (Build = test/JAR, CD = deploy)  
4. (Opcional) Environment variables — só **nomes** (`DB_URL`, `JWT_SECRET`…)

**9:30 Fechar**  
Voltar na home/health. “Fico à disposição.”

### Login demo

- UI: `ana.souza@alerta.local` / `Senha@123`

---

## 5) Links cola

```
App:     https://app-alerta-gs555427.azurewebsites.net
Health:  https://app-alerta-gs555427.azurewebsites.net/health
ADO:     https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps
Org:     https://dev.azure.com/RM555427
Task12:  https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps/_workitems/edit/12
YouTube: https://youtu.be/zi-IfYD7Q5I
RG:      rg-alerta-gs-devops
App name: app-alerta-gs555427
SQL:     sql-alerta-gs555427 / DB alertacidadao
```

---

## 6) Respostas rápidas (perguntas do professor)

| Pergunta | Resposta |
|----------|----------|
| Localhost? | Não — App Service + Azure SQL |
| Tabelas? | `ac_alerta` e `ac_atualizacao` |
| Persistência? | SELECT no Query Editor |
| Main protegida? | Policies: revisor + work item |
| Segredos? | App Settings, não no código |
| Pipeline? | YAML `azure-pipeline.yml` — CI test+package, CD deploy JAR |
| ACI? | Não usamos ACI; hospedagem = App Service (Dockerfiles no repo por requisito) |
| Cidadão? | Morador do bairro / alagamento / ODS 11–13 |

---

## 7) Comando único para o Gabriel colar no outro Cursor

```
Leia e execute o arquivo PLAYBOOK_OUTRO_PC_CURSOR.md (ou AlertaCidadao_PLAYBOOK_OUTRO_PC.md na Desktop).
Faça smoke check do /health, abra todas as URLs da seção 3 no navegador, ajude a autenticar Azure Portal + Azure DevOps + SQL Query Editor (SQL auth alertaadmin) + login do painel ana.souza@alerta.local, e deixe o roteiro da seção 4 visível. Não delete nada no Azure. No final diga o que já está aberto e o que eu faço na hora da apresentação.
```

---

## 8) Critérios de “pronto”

- [ ] `/health` = UP  
- [ ] Home do app abre  
- [ ] Painel logado (Ana)  
- [ ] Query Editor conectado (SQL auth)  
- [ ] ADO: Task 12, PRs, Policies, Pipeline  
- [ ] Diagrama/README aberto  
- [ ] Gabriel sabe a ordem dos 10 minutos (seção 4)
