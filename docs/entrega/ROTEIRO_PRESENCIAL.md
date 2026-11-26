# Roteiro presencial — AlertaCidadao (10 min · 30 pts)

**Aluno:** Gabriel Gomes Mancera · RM **555427** · Solo  
**Turma:** 2TDSPW · DevOps Tools & Cloud Computing  
**Data:** 23–27/11 (ordem por chegada)

---

## Antes de falar (checklist 60 s)

Abas prontas no notebook:

1. App: https://app-alerta-gs555427.azurewebsites.net  
2. Health: https://app-alerta-gs555427.azurewebsites.net/health  
3. Azure Portal → SQL `alertacidadao` → **Query editor** (já logado `alertaadmin`)  
4. ADO: https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps  
   - Boards → Task **#12**  
   - Repos → Pull requests (Completed) + Policies da `main`  
   - Pipelines → run verde  
5. Diagrama: `docs/ARQUITETURA_MACRO.md` (ou README no GitHub/Azure Repos)

Login demo UI: `ana.souza@alerta.local` / `Senha@123`  
(Senha SQL **fora da tela** se precisar digitar de novo.)

---

## Mapa dos 10 minutos

| Tempo | Bloco | Pontos |
|------:|-------|--------|
| 0:00–0:40 | Abertura CitizenTech | — |
| 0:40–4:00 | **1) App na nuvem + CRUD + SELECT** | **10** |
| 4:00–6:30 | **2) Arquitetura + CI/CD + por que Azure** | **10** |
| 6:30–9:30 | **3) Azure DevOps (Boards/Repos/Pipelines/policies)** | **10** |
| 9:30–10:00 | Fechamento + perguntas | — |

---

# 0) Abertura (~40 s)

**Na tela:** home do App Service (nuvem).

**Fala:**
> Olá, sou Gabriel Gomes Mancera, RM 555427.  
> Projeto solo **AlertaCidadao**, tema CitizenTech: o cidadão reporta alagamento no bairro e acompanha o atendimento.  
> Conecta às ODS 11 e 13.  
> A aplicação está no Azure App Service — não é localhost. Em dez minutos mostro o app funcionando, a arquitetura e o Azure DevOps.

---

# 1) App funcionando + CRUD + SELECT (~3 min 20 s) — 10 pts

## 1A — Prova de nuvem (~30 s)

**Na tela:** home → clicar **Health Azure** (ou abrir `/health`).

**Fala:**
> Health UP, serviço alerta-cidadao-api, versão publicada via pipeline.  
> URL pública azurewebsites.net — hospedado no App Service Java 21.

## 1B — CRUD na UI (~2 min)

**Na tela:** **Abrir painel da API** → Entrar com Ana Souza.

Ordem (falar o nome da operação):

1. **CREATE** → Publicar alerta (bairro + risco + descrição)  
2. **READ** → mostrar na lista / Atualizar lista  
3. **UPDATE** → Atualizar status do alerta  
4. **CREATE 2ª tabela** → Atualizações → enviar mensagem  
5. **UPDATE 2ª tabela** (se der tempo) → Atualizar na mensagem  
6. **DELETE** → Excluir atualização → Excluir alerta  

**Fala (enquanto faz):**
> Autenticação JWT.  
> CRUD na tabela ac_alerta e depois em ac_atualizacao, ligada ao alerta — as duas tabelas de negócio.  
> Create, read, update e delete.

## 1C — SELECT no banco (~50 s)

**Na tela:** Query editor Azure SQL (já logado).

```sql
SELECT * FROM ac_alerta;
SELECT * FROM ac_atualizacao;
```

**Fala:**
> No Azure SQL, banco alertacidadao, confirmo a persistência com SELECT — não uso só o GET da API.  
> Os registros do CRUD aparecem aqui.

*(Se acabou de deletar na UI, faça um CREATE rápido de novo antes do SELECT, ou mostre que sumiu após o DELETE.)*

---

# 2) Arquitetura (~2 min 30 s) — 10 pts

**Na tela:** `ARQUITETURA_MACRO.md` (diagrama mermaid) ou README.

**Fala (decorar esta estrutura):**
> A arquitetura macro é esta:  
> O cidadão usa o front React no mesmo App Service.  
> A API Spring Boot Java 21 recebe as chamadas em /api, com JWT.  
> Os dados ficam no Azure SQL, tabelas ac_alerta e ac_atualizacao, com Flyway.  
>  
> No Azure DevOps: Boards gera a tarefa, Repos guarda o código, a Pipeline faz CI — build, testes Maven — e CD — deploy do JAR no App Service.  
>  
> Por que esses recursos:  
> - **App Service** porque é PaaS simples para Java 21, com variável de ambiente para DB e JWT.  
> - **Azure SQL** porque a rubrica pede banco em nuvem e evidência com SELECT.  
> - **Azure DevOps** porque a disciplina exige Boards, Repos e Pipelines com policies na main.  
> - Segredos ficam em Application Settings, não hardcoded.

Apontar no diagrama: Boards → Repos → CI → CD → App → SQL.

---

# 3) Azure DevOps (~3 min) — 10 pts

## 3A — Boards (~40 s)

**Na tela:** Task **#12** (Done).

**Fala:**
> No Boards a Task 12 guiou a demonstração: bump da versão do health, PR, pipeline e validação do CRUD.  
> Está Closed/Done, com links de commit, PR e pipeline.

## 3B — Repos + policies (~1 min)

**Na tela:** PR Completed (#15) → depois **Policies** / Branch policies da `main`.

**Fala:**
> Fluxo: branch feature a partir da tarefa, alteração no HealthController (código-fonte, não README), push, Pull Request.  
> A main está protegida: mínimo de revisor e work item vinculado — policies blocking.  
> Eu aprovo e completo o merge; a pipeline dispara automaticamente.

## 3C — Pipelines (~1 min)

**Na tela:** Pipelines → run verde (Build + Deploy).

**Fala:**
> Pipeline alerta-ci-cd em YAML.  
> No Build: Maven test + package, publica o artefato JAR.  
> No Release/CD: deploy no App Service app-alerta-gs555427 pela service connection.  
> Agent self-hosted. Run verde prova CI e CD.

## 3D — Variáveis / segredos (~20 s)

**Na tela (opcional, 5 s):** App Service → Environment variables (mostrar **nomes**, não valores).

**Fala:**
> Conexão do banco e JWT_SECRET estão em variáveis de ambiente do App Service.  
> Não ficam no código-fonte.

---

# 4) Fechamento (~30 s)

**Na tela:** home ou health.

**Fala:**
> Resumindo: AlertaCidadao na nuvem, CRUD nas duas tabelas com prova no SQL, arquitetura App Service + Azure SQL + DevOps, e ciclo Boards, PR e pipeline.  
> Fico à disposição para perguntas.

---

## Respostas rápidas (se perguntarem)

| Pergunta | Resposta curta |
|----------|----------------|
| Por que não localhost? | Rubrica exige nuvem; app e SQL no Azure. |
| Quantas tabelas? | Duas de negócio: `ac_alerta` e `ac_atualizacao`. |
| Como prova persistência? | SELECT no Query Editor, não só GET. |
| O que protege a main? | Policy: revisor + work item vinculado. |
| Onde está o segredo? | App Settings (`DB_*`, `JWT_SECRET`). |
| O que a pipeline faz? | CI: test+JAR · CD: deploy App Service. |
| Quem é o cidadão? | Morador do bairro reportando alagamento (CitizenTech / ODS 11–13). |
| ACI? | Não usei container em ACI; hospedagem é **App Service** (código Java). Dockerfiles existem no repo por requisito de pasta. |

---

## Links cola (colar no bloco de notas)

```
App:     https://app-alerta-gs555427.azurewebsites.net
Health:  https://app-alerta-gs555427.azurewebsites.net/health
ADO:     https://dev.azure.com/RM555427/AlertaCidadao-GS-DevOps
Org:     https://dev.azure.com/RM555427
YouTube: https://youtu.be/zi-IfYD7Q5I
Login:   ana.souza@alerta.local / Senha@123
```

```sql
SELECT * FROM ac_alerta;
SELECT * FROM ac_atualizacao;
```

---

## Ensaiar

1. Cronometrar **uma vez completa** sem pausa.  
2. Se passar de 10 min: enxugar UPDATE da 2ª tabela e a aba de Environment variables.  
3. No dia: testar a URL **antes** de chegar (vários apps estavam down no check do professor).
