pipeline {
    agent any

    stages {
        // Étape 1 : Récupération du code depuis Git
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/Sayf']],  // Branche à utiliser
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }

        // Étape 2 : Construction du projet avec Maven
        stage('Maven Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        // Étape 3 : Exécution des tests unitaires
        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        // Étape 4 : Analyse du code avec SonarQube
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Assurez-vous que SonarQube est configuré dans Jenkins
                    withSonarQubeEnv('SonarQube') {  // Nom de la configuration SonarQube dans Jenkins
                        sh 'mvn sonar:sonar -Dsonar.projectKey=DevopsSkiStation -Dsonar.host.url=http://sonarqube:9000'
                    }
                }
            }
        }

        // Étape 5 : Construction de l'image Docker
        stage('Build Docker Image') {
            steps {
                script {
                    // Assurez-vous que Docker est installé sur l'agent Jenkins
                    sh 'docker build -t devops-ski-station:latest .'
                }
            }
        }

        // Étape 6 : Publication de l'image Docker (optionnel)
        stage('Push Docker Image') {
            steps {
                script {
                    // Authentification auprès du registre Docker (ex: Docker Hub)
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASSWORD'
                    }
                    // Tag et push de l'image
                    sh 'docker tag devops-ski-station:latest $DOCKER_USER/devops-ski-station:latest'
                    sh 'docker push $DOCKER_USER/devops-ski-station:latest'
                }
            }
        }
    }

    // Post-actions (optionnel)
    post {
        success {
            echo 'Pipeline exécuté avec succès !'
        }
        failure {
            echo 'Échec du pipeline. Consultez les logs pour plus de détails.'
        }
    }
}