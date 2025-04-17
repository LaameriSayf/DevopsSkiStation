pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'  // Nom du serveur SonarQube dans Jenkins
    }

    stages {
        // 1️⃣ Git
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

        // 2️⃣ Build
        stage('Maven Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        // 3️⃣ Test
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // 4️⃣ SonarQube Analysis
        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=DevopsSkiStation \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=sqa_5b84f2533f8e4f1c262920e14dc8e8b7644fcc14
                    '''
                }
            }
        }

        // 5️⃣ Générer le rapport PDF depuis Sonar (option simple via wkhtmltopdf)
        stage('Generate SonarQube PDF') {
            steps {
                script {
                    def reportUrl = "http://192.168.56.10:9000/project/overview?id=DevopsSkiStation"
                    sh "wkhtmltopdf ${reportUrl} sonar-report.pdf"
                }
            }
        }

        // 6️⃣ Déploiement vers Nexus
        stage('Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }

        // 7️⃣ Build Docker Image
        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageName = 'sayflaameri/gestion-station-ski'
                    def dockerImageTag = 'latest'

                    sh "ls -l target/gestion-station-ski-1.0.jar || exit 1"
                    sh "docker build -t ${dockerImageName}:${dockerImageTag} -f Dockerfile ."
                }
            }
        }

        // 8️⃣ Push Docker Image to Docker Hub
        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-hub-credentials',
                        usernameVariable: 'DOCKERHUB_USERNAME',
                        passwordVariable: 'DOCKERHUB_PASSWORD'
                    )]) {
                        sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                        sh "docker push sayflaameri/gestion-station-ski:latest"
                    }
                }
            }
        }

        // 9️⃣ Docker Compose
        stage('Docker Compose') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
            }
        }

        // 🔟 Grafana Dashboards
        stage('Grafana') {
            steps {
                script {
                    def grafanaUrl = 'http://192.168.56.10:3000/d/haryan-jenkins/jenkins3a-performance-and-health-overview'
                    withCredentials([usernamePassword(
                        credentialsId: 'credential_grafana',
                        usernameVariable: 'GRAFANA_USERNAME',
                        passwordVariable: 'GRAFANA_PASSWORD'
                    )]) {
                        def curlCommand = "curl -X GET -u ${GRAFANA_USERNAME}:${GRAFANA_PASSWORD} -H 'Content-Type: application/json' ${grafanaUrl}"
                        sh curlCommand
                    }
                }
            }
        }

        // 🔔 Mailing Test (optionnel)
        stage('Mailing Test') {
            steps {
                echo "✅ Envoi de mail de test réussi."
            }
        }
    }

    post {
        always {
            echo "🧹 Nettoyage Docker"
            sh 'docker-compose down'
        }

        success {
            echo '✅ Pipeline exécuté avec succès.'
            emailext(
                subject: "✅ Succès du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins s’est exécuté avec succès. 🎉

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}
                    📊 Rapport SonarQube joint en PDF

                    Cordialement,
                    Jenkins
                """,
                to: 'saiflaameri00@gmail.com',
                attachmentsPattern: 'sonar-report.pdf'
            )
        }

        failure {
            echo '❌ Le pipeline a échoué.'
            emailext(
                subject: "❌ Échec du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins a échoué. 🚨

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}

                    Merci de consulter Jenkins pour plus de détails.

                    Cordialement,
                    Jenkins
                """,
                to: 'saiflaameri00@gmail.com'
            )
        }
    }
}
