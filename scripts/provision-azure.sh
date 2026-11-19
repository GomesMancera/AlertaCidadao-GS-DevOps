#!/usr/bin/env bash
set -euo pipefail
: "${SQL_ADMIN_PASSWORD:?}"
: "${JWT_SECRET:?}"
LOCATION="${LOCATION:-brazilsouth}"
RG="${RG:-rg-alerta-gs-devops}"
SQL_SERVER="${SQL_SERVER:-sql-alerta-gs562279}"
SQL_DB="${SQL_DB:-alertacidadao}"
SQL_USER="${SQL_ADMIN_USER:-alertaadmin}"
PLAN="${PLAN:-plan-alerta-gs}"
WEBAPP="${WEBAPP:-app-alerta-gs562279}"

az group create --name "$RG" --location "$LOCATION"
az sql server create --name "$SQL_SERVER" --resource-group "$RG" --location "$LOCATION" --admin-user "$SQL_USER" --admin-password "$SQL_ADMIN_PASSWORD"
az sql server firewall-rule create --resource-group "$RG" --server "$SQL_SERVER" --name AllowAzureServices --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0 >/dev/null
az sql db create --resource-group "$RG" --server "$SQL_SERVER" --name "$SQL_DB" --service-objective Basic --backup-storage-redundancy Local
az appservice plan create --name "$PLAN" --resource-group "$RG" --location "$LOCATION" --sku B1 --is-linux
az webapp create --name "$WEBAPP" --resource-group "$RG" --plan "$PLAN" --runtime "JAVA:21-java21"
DB_URL="jdbc:sqlserver://${SQL_SERVER}.database.windows.net:1433;database=${SQL_DB};encrypt=true;trustServerCertificate=false;loginTimeout=30;"
az webapp config appsettings set --resource-group "$RG" --name "$WEBAPP" --settings \
  DB_URL="$DB_URL" DB_USER="$SQL_USER" DB_PASSWORD="$SQL_ADMIN_PASSWORD" \
  DB_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver" \
  HIBERNATE_DIALECT="org.hibernate.dialect.SQLServerDialect" \
  JWT_SECRET="$JWT_SECRET" ADMIN_EMAIL="gestor@alerta.local" ADMIN_SENHA="Gestor@123" \
  ADMIN_NOME="Gestor AlertaCidadao" WEBSITES_PORT="8080" >/dev/null
echo "Health: https://${WEBAPP}.azurewebsites.net/health"
