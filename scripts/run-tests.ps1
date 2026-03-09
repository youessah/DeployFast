# Script pour lancer les tests et générer le rapport de couverture
Write-Host "Lancement des tests automatisés..." -ForegroundColor Cyan
./mvnw clean test

if ($LASTEXITCODE -eq 0) {
    Write-Host "Tests réussis ! Génération du rapport Jacoco..." -ForegroundColor Green
    ./mvnw jacoco:report
    Write-Host "Ouverture du rapport de couverture..." -ForegroundColor Yellow
    Start-Process "target/site/jacoco/index.html"
} else {
    Write-Host "Des tests ont échoué. Veuillez vérifier les logs." -ForegroundColor Red
}
