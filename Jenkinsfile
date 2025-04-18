pipeline {
    agent any

    environment {
        PROJECT_NAME = '4TWIN5-G5-gestion-stationski'
        IMAGE_NAME = 'AbdulkareemMahmoud_G5_gestion-stationski'
        IMAGE_TAG = 'latest'
        NEXUS_REPO_URL = "http://192.168.33.10:8081/repository/gestionski/"
        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        SONARQUBE_URL = "http://192.168.33.10:9000"
        SONARQUBE_TOKEN = 'squ_7c92e6d1c16309ff7082a929b1985d5c1ca74a20'
        DOCKERHUB_CREDENTIALS = credentials('Docker_ID')
    }

    stages {
        stage('Clone') {
            steps {
                sh '''
                    rm -rf DevopsSkiStation || true
                    git clone --branch AbdulkareemMahmoud_4TWIN5_G https://github.com/LaameriSayf/DevopsSkiStation.git
                '''
            }
        }

        stage('Set Version') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn versions:set -DnewVersion=1.3.6-SNAPSHOT'
                }
            }
        }

        stage('Compile') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn test'
                }
            }
        }

      

        stage('Deploy to Nexus') {
            steps {
                dir('DevopsSkiStation') {
                    withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh "mvn deploy -DaltDeploymentRepository=nexus::default::${NEXUS_REPO_URL} -s .jenkins/settings.xml"
                    }
                }
            }
        }

        stage('Docker Image') {
            steps {
                dir('DevopsSkiStation') {
                    echo 'Building Docker image...'
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Docker Hub') {
            steps {
                script {
                    echo 'Logging into Docker Hub and pushing image...'
                    sh """
                        echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                        docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'docker compose up -d'
                }
            }
        }

        stage('Mail Test') {
            steps {
                emailext(
                    subject: "Test Email",
                    body: "Pipeline email test.",
                    to: 'negamex4274@gmail.com'
                )
            }
        }
    }

    post {
        success {
            emailext(
                subject: "✅ Pipeline Success - ${PROJECT_NAME}",
                body: "Pipeline completed successfully.\nProject: ${PROJECT_NAME}\nBuild: ${env.BUILD_NUMBER}\nStatus: ${currentBuild.currentResult}",
                to: 'negamex4274@gmail.com'
            )
        }
        failure {
            emailext(
                subject: "❌ Pipeline Failed - ${PROJECT_NAME}",
                body: "Pipeline failed.\nProject: ${PROJECT_NAME}\nBuild: ${env.BUILD_NUMBER}\nStatus: ${currentBuild.currentResult}",
                to: 'negamex4274@gmail.com'
            )
        }
    }
}
