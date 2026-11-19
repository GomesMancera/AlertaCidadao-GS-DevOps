# AlertaCidadao — teardown Azure (RODAR SÓ DEPOIS do presencial / correção)
# Corta custo apagando o Resource Group inteiro.
# Uso:
#   pwsh -File scripts/teardown-azure.ps1
#   pwsh -File scripts/teardown-azure.ps1 -ConfirmDelete

param(
    [string]$ResourceGroup = "rg-alerta-gs-devops",
    [switch]$ConfirmDelete
)

$ErrorActionPreference = "Stop"

Write-Host "AlertaCidadao teardown"
Write-Host "Resource Group: $ResourceGroup"
Write-Host ""

if (-not $ConfirmDelete) {
    Write-Host "DRY-RUN (nada será apagado)."
    Write-Host "Recursos atuais:"
    az resource list -g $ResourceGroup -o table
    Write-Host ""
    Write-Host "Para APAGAR de verdade (irreversível):"
    Write-Host "  pwsh -File scripts/teardown-azure.ps1 -ConfirmDelete"
    exit 0
}

Write-Host "ATENÇÃO: isto remove App Service, SQL e tudo do RG $ResourceGroup"
$answer = Read-Host "Digite DELETE para confirmar"
if ($answer -ne "DELETE") {
    Write-Host "Cancelado."
    exit 1
}

az group delete --name $ResourceGroup --yes --no-wait
Write-Host "Delete iniciado (async). Confira no Portal Azure até o RG sumir."
Write-Host "App e banco AlertaCidadao ficarão inacessíveis."
