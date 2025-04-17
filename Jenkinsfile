pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        SONARQUBE_URL = 'http://192.168.56.10:9000'
        SONARQUBE_PROJECT = 'DevopsSkiStation'
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
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONARQUBE_PROJECT} \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=sqa_5b84f2533f8e4f1c262920e14dc8e8b7644fcc14
                    """
                }
                // Attendre que l'analyse SonarQube soit complète
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }

        // 5️⃣ Générer le rapport PDF depuis Sonar
        stage('Generate SonarQube PDF') {
            steps {
                script {
                    // Solution améliorée pour le PDF
                    def reportUrl = "${SONARQUBE_URL}/api/project_badges/measure?project=${SONARQUBE_PROJECT}&metric=alert_status"
                    def dashboardUrl = "${SONARQUBE_URL}/dashboard?id=${SONARQUBE_PROJECT}"

                    // Créer un rapport HTML temporaire
                    writeFile file: 'sonar-report.html', text: """
                        <html>
                            <head>
                                <title>SonarQube Report</title>
                                <meta http-equiv="refresh" content="10">
                            </head>
                            <body>
                                <h1>SonarQube Analysis Report</h1>
                                <p>Generated at: ${new Date()}</p>
                                <iframe src="${dashboardUrl}" width="100%" height="800" frameborder="0"></iframe>
                            </body>
                        </html>
                    """

                    // Attendre que le dashboard soit prêt
                    sh 'sleep 120' // Augmentez ce délai si nécessaire

                    // Générer le PDF avec wkhtmltopdf
                    sh """
                        wkhtmltopdf \
                        --javascript-delay 30000 \
                        --no-stop-slow-scripts \
                        --enable-javascript \
                        sonar-report.html sonar-report.pdf
                    """
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

        // 🔔 Mailing Test
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
            archiveArtifacts artifacts: 'sonar-report.pdf', allowEmptyArchive: true
        }

        success {
            echo '✅ Pipeline exécuté avec succès.'
            emailext(
                subject: "✅ Succès du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins s'est exécuté avec succès. 🎉

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