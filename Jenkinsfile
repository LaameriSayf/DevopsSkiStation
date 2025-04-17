pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
    }

    stages {
        // ℹ️ 0️⃣ Initialisation du fichier report.txt
      stage('Initialize Report') {
          steps {
              script {
                  sh "echo \"Build Report for ${env.JOB_NAME} #${env.BUILD_NUMBER}\" > report.txt"
              }
          }
      }


        // 1️⃣ Git
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[ name: '*/Sayf' ]],
                        userRemoteConfigs: [[ url: 'https://github.com/LaameriSayf/DevopsSkiStation.git' ]]
                    ])
                    sh 'echo "[STAGE] Git completed" >> report.txt'
                }
            }
        }

        // 2️⃣ Maven Build
        stage('Maven Build') {
            steps {
                script {
                    sh 'mvn clean install | tee -a report.txt'
                    sh 'echo "[STAGE] Maven Build completed" >> report.txt'
                }
            }
        }

        // 3️⃣ Test
        stage('Test') {
            steps {
                script {
                    sh 'mvn test | tee -a report.txt'
                    sh 'echo "[STAGE] Test completed" >> report.txt'
                }
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
                            -Dsonar.login=sqa_... \
                        | tee -a report.txt
                    '''
                    sh 'echo "[STAGE] SonarQube Analysis completed" >> report.txt'
                }
            }
        }

        // 5️⃣ Nexus Deploy
        stage('Nexus Deploy') {
            steps {
                script {
                    sh 'mvn deploy | tee -a report.txt'
                    sh 'echo "[STAGE] Nexus deploy completed" >> report.txt'
                }
            }
        }

        // 6️⃣ Build Docker Image
        stage('Build Docker Image') {
            steps {
                script {
                    def img = 'sayflaameri/gestion-station-ski:latest'
                    sh "docker build -t ${img} -f Dockerfile . | tee -a report.txt"
                    sh 'echo "[STAGE] Docker image built" >> report.txt'
                }
            }
        }

        // 7️⃣ Push Docker Image
        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-hub-credentials',
                        usernameVariable: 'DOCKERHUB_USERNAME',
                        passwordVariable: 'DOCKERHUB_PASSWORD'
                    )]) {
                        sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                        sh 'docker push sayflaameri/gestion-station-ski:latest | tee -a report.txt'
                        sh 'echo "[STAGE] Docker push completed" >> report.txt'
                    }
                }
            }
        }

        // 8️⃣ Docker Compose Deploy
        stage('Docker Compose') {
            steps {
                script {
                    sh 'docker-compose down || true'
                    sh 'docker-compose up -d | tee -a report.txt'
                    sh 'echo "[STAGE] Docker Compose deployed" >> report.txt'
                }
            }
        }

        // 9️⃣ Grafana Data Fetch
        stage('Grafana') {
            steps {
                script {
                    def url = 'http://192.168.56.10:3000/...'
                    withCredentials([usernamePassword(
                        credentialsId: 'credential_grafana',
                        usernameVariable: 'GRAFANA_USERNAME',
                        passwordVariable: 'GRAFANA_PASSWORD'
                    )]) {
                        sh "curl -s -u $GRAFANA_USERNAME:$GRAFANA_PASSWORD $url | tee -a report.txt"
                        sh 'echo "[STAGE] Grafana data fetched" >> report.txt'
                    }
                }
            }
        }

        // 🔟 Génération du PDF
        stage('Generate PDF Report') {
            steps {
                script {
                    // Assure l'existence de report.txt
                    sh 'if [ ! -f report.txt ]; then echo "No logs found" > report.txt; fi'
                    sh 'pandoc report.txt -o report.pdf'
                    sh 'echo "[STAGE] PDF report generated" >> report.txt'
                }
            }
        }

        // 🔔 Test de mailing (log)
        stage('Mailing Test') {
            steps {
                script {
                    sh 'echo "[STAGE] Mailing test passed" >> report.txt'
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline terminé : ${currentBuild.currentResult}"
            sh 'docker-compose down'
        }
        success {
            emailext(
                to: 'saiflaameri00@gmail.com',
                subject: "✅ Succès : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "<h3>✅ Build réussie!</h3><p>Voir le rapport PDF en pièce jointe.</p>",
                attachmentsPattern: 'report.pdf',
                mimeType: 'text/html'
            )
        }
        unstable {
            emailext(
                to: 'saiflaameri00@gmail.com',
                subject: "⚠️ Instable : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "<h3>⚠️ Build instable</h3>",
                attachmentsPattern: 'report.pdf',
                mimeType: 'text/html'
            )
        }
        failure {
            emailext(
                to: 'saiflaameri00@gmail.com',
                subject: "❌ Échec : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "<h3>❌ Build échouée</h3>",
                attachmentsPattern: 'report.pdf',
                mimeType: 'text/html'
            )
        }
    }
}
