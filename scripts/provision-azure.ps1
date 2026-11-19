<#
.SYNOPSIS
  Provisiona Azure do AlertaCidadao (GS DevOps 2026/2).
.EXAMPLE
  .\scripts\provision-azure.ps1 -SqlAdminPassword 'SenhaForte!123' -JwtSecret 'chave-aleatoria-com-32-chars-minimo!!'
#>
param(
    [string]$SubscriptionId = $env:AZURE_SUBSCRIPTION_ID,
    [string]$Location = "brazilsouth",
    # SP Azure for Students: criar recursos no RG ja existente (nao cria RG novo)
    [string]$ResourceGroup = "rg-petcare-gs-devops",
    [string]$SqlServerName = "sql-alerta-gs562279",
    [string]$SqlDbName = "alertacidadao",
    [string]$SqlAdminUser = "alertaadmin",
    [Parameter(Mandatory = $true)][string]$SqlAdminPassword,
    [string]$AppServicePlan = "plan-alerta-gs",
    [string]$WebAppName = "app-alerta-gs562279",
    [string]$AdminEmail = "gestor@alerta.local",
    [string]$AdminSenha = "Gestor@123",
    [Parameter(Mandatory = $true)][string]$JwtSecret
)

$ErrorActionPreference = "Stop"
if ($SubscriptionId) { az account set --subscription $SubscriptionId }

Write-Host "==> Resource Group: $ResourceGroup"
az group create --name $ResourceGroup --location $Location | Out-Null

Write-Host "==> SQL Server: $SqlServerName"
az sql server create --name $SqlServerName --resource-group $ResourceGroup --location $Location --admin-user $SqlAdminUser --admin-password $SqlAdminPassword | Out-Null

az sql server firewall-rule create --resource-group $ResourceGroup --server $SqlServerName --name AllowAzureServices --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0 | Out-Null
try {
    $myIp = (Invoke-RestMethod -Uri "https://api.ipify.org").Trim()
    az sql server firewall-rule create --resource-group $ResourceGroup --server $SqlServerName --name AllowClientIp --start-ip-address $myIp --end-ip-address $myIp | Out-Null
    Write-Host "    Client IP: $myIp"
} catch { Write-Warning "IP publico nao detectado" }

Write-Host "==> Database: $SqlDbName"
az sql db create --resource-group $ResourceGroup --server $SqlServerName --name $SqlDbName --service-objective Basic --backup-storage-redundancy Local | Out-Null

Write-Host "==> App Service Plan + Web App"
az appservice plan create --name $AppServicePlan --resource-group $ResourceGroup --location $Location --sku B1 --is-linux | Out-Null
az webapp create --name $WebAppName --resource-group $ResourceGroup --plan $AppServicePlan --runtime "JAVA:21-java21" | Out-Null

$dbUrl = "jdbc:sqlserver://${SqlServerName}.database.windows.net:1433;database=${SqlDbName};encrypt=true;trustServerCertificate=false;loginTimeout=30;"
az webapp config appsettings set --resource-group $ResourceGroup --name $WebAppName --settings `
  DB_URL="$dbUrl" DB_USER="$SqlAdminUser" DB_PASSWORD="$SqlAdminPassword" `
  DB_DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver" `
  HIBERNATE_DIALECT="org.hibernate.dialect.SQLServerDialect" `
  JWT_SECRET="$JwtSecret" ADMIN_EMAIL="$AdminEmail" ADMIN_SENHA="$AdminSenha" `
  ADMIN_NOME="Gestor AlertaCidadao" WEBSITES_PORT="8080" | Out-Null
az webapp config set --resource-group $ResourceGroup --name $WebAppName --always-on true | Out-Null

Write-Host "OK https://$WebAppName.azurewebsites.net/health"
