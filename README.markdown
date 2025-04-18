# 🎿 DevopsSkiStation

**DevopsSkiStation** est une application de gestion d'une station de ski, conçue pour gérer les pistes et les skieurs via un backend robuste. Ce projet intègre une pipeline CI/CD complète pour automatiser le développement, les tests, et le déploiement.

---

## 🚀 Description du Projet

L'application permet de :

- **Gérer les pistes** : Ajouter, modifier, supprimer et filtrer les pistes (nom, couleur, longueur, pente).
- **Gérer les skieurs** : Associer des skieurs aux pistes via des relations.
- **Automatisation DevOps** : Une pipeline CI/CD avec Jenkins, SonarQube, Nexus, Docker, et Kubernetes pour un déploiement fluide.

Ce projet démontre les meilleures pratiques en développement backend et en DevOps, avec une intégration continue et un déploiement automatisé.

---

## 🛠️ Technologies Utilisées

### Backend

- **Java 17** avec **Spring Boot 3.3.3**
- **Spring Data JPA** pour la persistance
- **MySQL** comme base de données
- **Maven** pour la gestion des dépendances

### DevOps

- **Jenkins** : Pipeline CI/CD
- **SonarQube** : Analyse de la qualité du code
- **Nexus** : Gestion des artefacts
- **Docker** : Conteneurisation
- **Kubernetes** : Orchestration via Helm
- **Trivy** : Scan de sécurité des images Docker
- **Git** : Contrôle de version via GitHub

---

## 📋 Prérequis

Pour exécuter ce projet localement, assurez-vous d'avoir :

- **Java 17** (JDK)
- **Docker** et **Docker Compose**
- **kubectl** et **Helm** (pour Kubernetes)
- **Maven 3.8+**
- **MySQL** (ou utiliser le conteneur Docker fourni)
- Accès à un compte **Docker Hub** pour pousser/télécharger les images
- Accès à un serveur **Jenkins**, **SonarQube**, et **Nexus** (ou configurez-les localement)

---

## 🏁 Installation et Exécution

### 1. Cloner le dépôt

```bash
git clone https://github.com/LaameriSayf/DevopsSkiStation.git
cd DevopsSkiStation
```

### 2. Configurer le Backend

1. Mettez à jour `src/main/resources/application.properties` avec vos paramètres MySQL :

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/test_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

2. Compilez et exécutez le backend :

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### 3. Exécuter avec Docker Compose

1. Assurez-vous que `docker-compose.yml` est configuré correctement.

2. Lancez les services :

   ```bash
   export IMAGE_TAG=v125
   docker-compose up -d
   ```

3. Accédez au backend sur `http://localhost:8090` et à MySQL sur le port `3307`.

### 4. Déploiement sur Kubernetes

1. Assurez-vous que votre cluster Kubernetes est accessible via `kubectl`.

2. Déployez avec Helm :

   ```bash
   helm upgrade --install devopsskistation helm --namespace default --set image.tag=v125
   ```

3. Vérifiez les pods et services :

   ```bash
   kubectl get pods -n default
   kubectl get svc -n default
   ```

---

## 🧪 Tests et Qualité

- **Tests unitaires** : Exécutés avec JUnit et Maven (`mvn test`).
- **Couverture de code** : Analysée avec JaCoCo (voir `target/site/jacoco/index.html`).
- **Analyse statique** : SonarQube est intégré dans la pipeline CI/CD. Accédez au tableau de bord sur `http://192.168.33.10:9000`.
- **Scan de sécurité** : Les images Docker sont scannées avec Trivy pour détecter les vulnérabilités.

---

## 📦 Pipeline CI/CD

La pipeline Jenkins automatise les étapes suivantes :

 1. **Checkout** : Récupération du code depuis GitHub.
 2. **Test Docker** : Vérification de l'environnement Docker.
 3. **Start MySQL** : Lancement d'un conteneur MySQL pour les tests.
 4. **Build** : Compilation du projet avec Maven.
 5. **Test with Coverage** : Exécution des tests unitaires avec JaCoCo.
 6. **SonarQube Analysis** : Analyse de la qualité du code.
 7. **Deploy to Nexus** : Publication des artefacts.
 8. **Build and Push Docker Image** : Création et publication de l'image Docker (`ouday2025/devopsskistation:v125`).
 9. **Scan Docker Image** : Analyse des vulnérabilités avec Trivy.
10. **Docker Compose** : Déploiement local des services.
11. **Deploy to Kubernetes** : Déploiement sur un cluster via Helm.

Consultez les logs complets sur `http://192.168.33.10:8080/job/sonar/`.

---

## 🐞 Dépannage

- **Problème de connexion MySQL** : Vérifiez les variables d'environnement dans `docker-compose.yml` ou `application.properties`.
- **Échec de la pipeline** : Consultez les logs Jenkins pour des erreurs spécifiques.
- **Image Docker non trouvée** : Assurez-vous que l'image `ouday2025/devopsskistation:v125` est disponible sur Docker Hub ou construisez-la localement.
- **Problèmes Kubernetes** : Vérifiez les pods avec `kubectl describe pod <nom-du-pod>`.

---

**❄️ Happy Skiing & Coding! ❄️**