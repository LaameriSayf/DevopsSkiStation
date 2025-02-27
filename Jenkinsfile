pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'  // Le nom du serveur SonarQube configuré dans Jenkins
    }

    stages {
        // 1️⃣ Stage Git : Récupérer le code depuis Git
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/Sayf']],  // Branche correcte
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }

        // 2️⃣ Stage Maven Build : Build du projet avec Maven
        stage('Maven Build') {
            steps {
                script {
                    // Exécution de la commande Maven pour nettoyer et construire le projet
                    sh 'mvn clean install'
                }
            }
        }

        // 3️⃣ Stage SonarQube : Analyse du code avec SonarQube
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Exécution de l'analyse SonarQube avec le plugin SonarQube Scanner
                    // Assurez-vous que vous avez configuré SonarQube dans Jenkins
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=DevopsSkiStation \
                        -Dsonar.host.url=http://your-sonarqube-server-url \
                        -Dsonar.login=your-sonarqube-token
                    '''
                }
            }
        }

        // 4️⃣ Stage Test : Lancer les tests Maven
        stage('Test') {
            steps {
                script {
                    // Exécution de la commande Maven pour lancer les tests
                    sh 'mvn test'
                }
            }
        }
    }
}
