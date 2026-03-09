# Script pour vérifier les dépendances (DevSecOps)
Write-Host "Lancement de l'audit des dépendances..." -ForegroundColor Cyan
./mvnw org.owasp:dependency-check-maven:check

if ($LASTEXITCODE -eq 0) {
    Write-Host "Audit de sécurité terminé. Vérifiez le rapport dans target/dependency-check-report.html" -ForegroundColor Green
    Start-Process "target/dependency-check-report.html"
} else {
    Write-Host "Des vulnérabilités critiques ont été détectées." -ForegroundColor Red
}
