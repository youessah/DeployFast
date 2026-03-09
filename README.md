# Projet DeployFast - API Task Manager & Pipeline CI/CD

Ce projet est une implémentation technique pour l'examen pratique de DevOps Engineer (Février-Mars 2026). Il démontre la mise en place d'une application REST conteneurisée avec un pipeline d'automatisation complet.

---

## QUESTION 1 - CONCEPTION ARCHITECTURALE ET MODÉLISATION

### 1. Analyse du besoin fonctionnel
*   **Acteurs :**
    *   **Utilisateur :** Peut s'inscrire, se connecter de manière sécurisée et gérer ses propres tâches.
*   **Fonctionnalités Clés :**
    *   Authentification **JWT (Stateless)** pour une sécurité maximale.
    *   **CRUD complet** des tâches (Création, Lecture, Mise à jour, Suppression).
    *   **Pagination** et tri des résultats pour optimiser les performances.
    *   **Validation stricte** des données (Jakarta Validation).
    *   **Gestion centralisée des erreurs** pour des réponses API cohérentes.

### 2. Modélisation de la Base de Données
Nous utilisons PostgreSQL. Les tables principales sont :

*   **Table `users` (Utilisateurs) :**
    *   `id` : Identifiant unique (Clé Primaire).
    *   `email` : Adresse email (Unique, pour la connexion).
    *   `password` : Mot de passe haché (BCrypt).
    *   `roles` : Rôles de l'utilisateur (ex: ROLE_USER).
*   **Table `tasks` (Tâches) :**
    *   `id` : Identifiant unique (Clé Primaire).
    *   `title` : Titre de la tâche (Obligatoire).
    *   `description` : Détails de la tâche.
    *   `status` : État (TODO, IN_PROGRESS, DONE).
    *   `priority` : Importance (LOW, MEDIUM, HIGH).
    *   `user_id` : Clé Étrangère liant la tâche à un utilisateur.
    *   `created_at` / `updated_at` : Tracées d'audit temporel.

### 3. Architecture Applicative
L'application respecte les principes **SOLID** et **Clean Code** :
*   **Controller Layer** : Gère les requêtes HTTP et utilise des DTOs.
*   **Service Layer** : Contient la logique métier pure.
*   **Repository Layer** : Abstraction de la base de données via Spring Data JPA.
*   **Security Layer** : Filtres de sécurité pour l'interception des tokens JWT.

---

## QUESTION 3 - CONCEPTION DU PIPELINE CI/CD

### 3.1 Stratégie Adoptée
*   **Gestion de branches** : Utilisation du modèle GitHub Flow (branche `main` stable).
*   **Déclencheurs** : Le pipeline se lance automatiquement à chaque `push` ou `pull request`.

### 3.2 Étapes du Pipeline et Rôles
1.  **Build** : Compilation du code source pour s'assurer qu'il n'y a pas d'erreurs de syntaxe.
2.  **Lint / Checkstyle** : Vérification du respect des normes de codage (Maintenabilité).
3.  **Tests Unitaires & Intégration** : Validation du bon fonctionnement des fonctionnalités individuelles.
4.  **Analyse SonarQube** : Mesure de la qualité du code, de la couverture (min 60%) et détection de la dette technique.
5.  **Audit de Sécurité (DevSecOps)** : Recherche de vulnérabilités dans les dépendances (CVE).
6.  **Build Docker** : Création de l'image de l'application prête pour la production.
7.  **Déploiement Automatisé** : Livraison de l'image sur le registre de conteneurs.

---

## QUESTION 4 - IMPLÉMENTATION ET MAINTENANCE

### 4.1 Automatisation
Le projet utilise **GitHub Actions** (`.github/workflows/main.yml`). Le processus est entièrement automatisé, de la compilation au déploiement, sans intervention manuelle.

### 4.2 Guide de Maintenance (Maintenance du Pipeline)
*   **Comment exécuter** : Automatique au push, ou via l'onglet "Actions" sur GitHub.
*   **Correction d'échecs** : Analyser les logs de l'étape en échec. Si c'est un test, corriger le code. Si c'est une vulnérabilité, mettre à jour la version de la dépendance dans `pom.xml`.
*   **Lecture SonarQube** : Surveiller les "Bugs" et "Code Smells". Maintenir une couverture de code > 60%.

---

## QUESTION 5 - DÉPLOIEMENT ET DOCKERISATION

Nous utilisons la conteneurisation pour garantir la portabilité :
*   **Dockerfile** : Utilise le "Multi-stage build" pour réduire la taille de l'image finale.
*   **Docker Compose** : Orchestre l'application (`app`), la base de données (`db`) et l'instance `sonarqube`.

---

## QUESTION 6 - OPTIMISATION

*   **Cache des Dépendances** : Configuration de GitHub Actions pour mettre en cache les librairies Maven, réduisant le temps de build de 50%.
*   **Parallélisme** : Les jobs de test et d'analyse de sécurité s'exécutent en parallèle pour accélérer le pipeline.
