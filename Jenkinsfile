pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        DOCKER_IMAGE = 'sayflaameri/gestion-station-ski:latest'
    }

    stages {

        // 1️⃣ Récupération du code source
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/Sayf']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }

        // 2️⃣ Build Maven
        stage('Maven Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        // 3️⃣ Exécution des tests
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // 4️⃣ Analyse SonarQube
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv("${SONARQUBE_SERVER}") {
                        sh '''
                            mvn sonar:sonar \
                            -Dsonar.projectKey=DevopsSkiStation \
                            -Dsonar.host.url=http://192.168.56.10:9000 \
                            -Dsonar.login=sqa_5b84f2533f8e4f1c262920e14dc8e8b7644fcc14
                        '''
                    }
                }
            }
        }

        // 5️⃣ Déploiement Nexus
        stage('Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        // 6️⃣ Construction de l'image Docker
        stage('Build Docker Image') {
            steps {
                script {
                    // Vérifie que le fichier JAR existe
                    sh 'ls -l target/gestion-station-ski-1.0.jar || exit 1'

                    // Build de l'image Docker avec Dockerfile à la racine du projet
                    sh 'docker build -t ${DOCKER_IMAGE} -f Dockerfile .'
                }
            }
        }

        // 7️⃣ Push vers DockerHub
        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        // 8️⃣ Déploiement avec Docker Compose
        stage('Docker Compose') {
            steps {
                script {
                    sh 'docker-compose down || true'
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline exécutée avec succès !'
        }
        failure {
            echo '❌ Échec de la pipeline.'
        }
    }
}
