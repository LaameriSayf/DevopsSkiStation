pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'  // Nom du serveur SonarQube configuré dans Jenkins
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
                    sh 'mvn clean install'
                }
            }
        }

        // 3️⃣ Stage Test : Lancer les tests Maven
        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        // 4️⃣ Stage SonarQube : Analyse du code avec SonarQube
        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=DevopsSkiStation \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=squ_4e10e3d4fcaf8920934a3edeb3e941da55baea87
                    '''
                }
            }
        }

        // 5️⃣ Stage Nexus Deployment : Déploiement sur Nexus Repository
        stage('Nexus Deployment') {
            steps {
                sh 'mvn deploy -DskipTests'

            }
        }
    }
}
