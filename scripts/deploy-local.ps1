# Script pour déployer l'application localement avec Docker
Write-Host "Préparation du déploiement Docker..." -ForegroundColor Cyan
Write-Host "Arrêt des anciens conteneurs..." -ForegroundColor Yellow
docker compose down

Write-Host "Construction et lancement de l'environnement..." -ForegroundColor Cyan
docker compose up --build -d

if ($LASTEXITCODE -eq 0) {
    Write-Host "------------------------------------------------" -ForegroundColor Green
    Write-Host "Déploiement réussi !" -ForegroundColor Green
    Write-Host "Application : http://localhost:8080" -ForegroundColor White
    Write-Host "SonarQube   : http://localhost:9000" -ForegroundColor White
    Write-Host "------------------------------------------------" -ForegroundColor Green
} else {
    Write-Host "Erreur lors du déploiement Docker." -ForegroundColor Red
}
