# 📌 Ski Station Management – CI/CD avec Docker, Jenkins & Monitoring

## 🧾 Description Générale
Application Spring Boot permettant la gestion d’une station de ski (skieurs, inscriptions, cours…). Elle est intégrée dans une pipeline CI/CD complète avec Docker, Jenkins, Nexus, Prometheus, Grafana, Trivy et notifications mails.

---

## 🧱 Architecture Technique

1. **Backend** : Spring Boot
2. **Base de données** : MySQL (sur VM Vagrant)
3. **Monitoring** : Prometheus + Grafana
4. **CI/CD** : Jenkins
5. **Sécurité** : Scan Trivy
6. **Qualité de code** : SonarQube
7. **Stockage des artefacts** : Nexus
8. **Notifications** : Jenkins via email
9. **Containerisation** : Docker + Docker Compose

---

## 🧪 Entités et Fonctionnalités Backend

- **Registration**
    - Ajout simple : `/registration/addRegistration`
    - Ajout & assignation : `/registration/addAndAssignToSkier/{id}`
    - Suppression : `/registration/deleteRegistration/{id}`
    - Mise à jour : `/registration/updateRegistration/{id}`
    - Récupération : `/registration/getAllSubs`

---

## 🐳 Docker

- Image backend créée via Dockerfile :
docker build -t halimtrabelsi/gestionstationski-app:0.0.1 .


- Lancement via Docker Compose :
docker compose up -d

## 📦 Nexus
Utilisé pour héberger l’artefact .jar du backend :
mvn deploy -DskipTests

## 🔐 Trivy
 Scan de vulnérabilités :
- trivy image --severity CRITICAL halimtrabelsi/gestionstationski-app:0.0.1 > trivy-report.txt

## 🔬 SonarQube
Analyse de qualité de code :

- mvn sonar:sonar

## 📈 Monitoring avec Prometheus & Grafana
⚙️ Prometheus
Collecte des métriques exposées par Spring Boot (via Actuator).

Exemple prometheus.yml :
scrape_configs:
- job_name: 'spring-boot'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['app:8080']
## 📊 Grafana
 Dashboard connectée à Prometheus.
 Adresse : http://localhost:3000 (admin/admin)
 
## ⚙️ Jenkins
Pipeline Jenkins (Jenkinsfile) automatisé :

.Checkout du code

.Compilation (mvn clean install)

.Test

.Analyse Sonar

.Déploiement Nexus

.Build + Push Docker Image

.Déploiement via Docker Compose

.Scan Trivy

.Notification email

## 📬 Emailing Jenkins
Envoi d’un email après le pipeline (succès ou échec) :

emailext (
subject: "✅ Pipeline terminé",
body: "<p>Succès du pipeline Jenkins 🚀</p>",
mimeType: 'text/html',
to: 'halimtrabelsi73@gmail.com'
)

## 💾 Base de données
MySQL hébergée dans la VM Vagrant

IP : 192.168.33.10, Port : 3306

BDD : stationski

## 📁 Structure du Projet

├── backend/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── jenkins/
│   └── Jenkinsfile
├── docker-compose.yml
├── prometheus.yml
└── README.md

## 🧪 Test Postman
POST http://192.168.33.10:8089/api/registration/addRegistration
Content-Type: application/json

{
"numWeek": 14,
"skier": { "id": 1 },
"course": { "id": 1 }
}

## 👤 Auteur
## Halim Trabelsi

## 📧 halimtrabelsi73@gmail.com

## 🔗 GitHub Repo : https://github.com/HalimTrabelsi