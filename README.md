# Project DeployFast - Task Manager API & CI/CD Pipeline

This project is a technical implementation for the DevOps Engineer practical exam (February-March 2026).

## QUESTION 1 - CONCEPTION ARCHITECTURALE ET MODÉLISATION

### 1. Analyse du besoin fonctionnel
*   **Acteurs :**
    *   **Utilisateur :** Peut s'inscrire, se connecter, et gérer ses tâches personnelles.
*   **Principales fonctionnalités :**
    *   Authentification sécurisée via JWT.
    *   Gestion de tâches (CRUD : Créer, Lire, Mettre à jour, Supprimer).
    *   Pagination des résultats pour la liste des tâches.
    *   Validation des données en entrée.
    *   Gestion centralisée des erreurs.
*   **Contraintes techniques :**
    *   Utilisation de Java 25 avec Spring Boot 4.
    *   Architecture modulaire (Spring Modulith).
    *   Sécurité DevSecOps intégrée.
    *   Pipeline CI/CD complet avec SonarQube.
    *   Conteneurisation avec Docker.

### 2. Modélisation des données
*   **Table `users` :**
    *   `id` (PK, Long, Auto-increment)
    *   `email` (String, Unique, Not Null)
    *   `password` (String, Not Null)
    *   `roles` (String)
*   **Table `tasks` :**
    *   `id` (PK, Long, Auto-increment)
    *   `title` (String, Not Null)
    *   `description` (Text)
    *   `status` (Enum: TODO, IN_PROGRESS, DONE)
    *   `priority` (Enum: LOW, MEDIUM, HIGH)
    *   `user_id` (FK referencing `users.id`)
    *   `created_at` (Timestamp)
    *   `updated_at` (Timestamp)

### 3. Structure REST de l'API
*   **Authentification :**
    *   `POST /api/v1/auth/register` (201 Created)
    *   `POST /api/v1/auth/login` (200 OK, retourne JWT)
*   **Tâches :**
    *   `GET /api/v1/tasks?page=0&size=10` (200 OK)
    *   `GET /api/v1/tasks/{id}` (200 OK or 404 Not Found)
    *   `POST /api/v1/tasks` (201 Created)
    *   `PUT /api/v1/tasks/{id}` (200 OK or 400 Bad Request)
    *   `DELETE /api/v1/tasks/{id}` (204 No Content)

### 4. Architecture applicative
L'architecture suit les principes **SOLID** et **Clean Code** :
*   **Controllers :** Gestion des entrées/sorties HTTP, utilisation de DTOs.
*   **Services :** Logique métier encapsulée, indépendante de la couche transport.
*   **Repositories :** Abstraction de la couche de données avec Spring Data JPA.
*   **Security :** Filtres JWT pour une authentification stateless.
*   **Modularity :** Utilisation de Spring Modulith pour assurer le découpage fonctionnel.

## QUESTION 3 - CONCEPTION DU PIPELINE
### 1.1 Analyse et stratégie CI/CD
*   **Stratégie de branches :** GitHub Flow (main pour la prod, branches de feature).
*   **Déclencheurs :** Push sur `main` et `develop`, Pull Requests vers `main`.
*   **Étapes du pipeline :**
    1.  **Build** : Compilation du code.
    2.  **Lint** : Vérification du style de code.
    3.  **Tests unitaires** : Validation fonctionnelle atomique.
    4.  **Analyse SonarQube** : Qualité de code et couverture.
    5.  **Scan de sécurité** : Scan de vulnérabilités (Snyk/Trivy).
    6.  **Build Docker** : Création de l'image conteneurisée.
    7.  **Déploiement** : Push vers le registre.

## QUESTION 4 - IMPLÉMENTATION DU PIPELINE
Le pipeline est configuré dans `.github/workflows/main.yml`. Il assure l'automatisation complète sans intervention manuelle, intégrant SonarQube pour la détection de bugs et la dette technique, ainsi que des scans DevSecOps pour la sécurité des dépendances.

## QUESTION 5 - DÉPLOIEMENT AUTOMATIQUE
Mise en place d'un `Dockerfile` multi-stage pour optimiser la taille de l'image et d'un `docker-compose.yml` pour orchestrer l'application avec sa base de données PostgreSQL et SonarQube.

## QUESTION 6 - OPTIMISATION & CLEAN CODE
*   **Modularité :** Découpage clair entre Controller, Service et Repository.
*   **Clean Code :** Méthodes courtes, nommage explicite, pas de duplication.
*   **Optimisation Pipeline :** Utilisation du cache Maven pour accélérer les builds et parallélisation des jobs de scan et de qualité.
