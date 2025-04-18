pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mahmoudabdulkareem/gestion-stationski'
        IMAGE_TAG = 'latest'
        NEXUS_REPO_URL = "http://192.168.33.10:8081/repository/gestionski/"
        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        DOCKERHUB_CREDENTIALS = credentials('Docker_ID')
        SONARQUBE_URL = "http://192.168.33.10:9000"
        SONARQUBE_ENV = 'SonarQube_Credentials'
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
                sh 'mvn versions:set -DnewVersion=1.3.6-SNAPSHOT'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=gestion-stationski -Dsonar.host.url=${SONARQUBE_URL}'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh "mvn deploy -DaltDeploymentRepository=nexus::default::${NEXUS_REPO_URL} -s .jenkins/settings.xml"
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh """
                    docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                    echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker compose up -d'
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
                subject: "Pipeline Success - DevopsSkiStation",
                body: "Pipeline completed successfully.\nProject: DevopsSkiStation\nDate: ${new Date()}",
                to: 'negamex4274@gmail.com'
            )
        }
        failure {
            emailext(
                subject: "Pipeline Failed - DevopsSkiStation",
                body: "Pipeline failed.\nProject: DevopsSkiStation\nDate: ${new Date()}",
                to: 'negamex4274@gmail.com'
            )
        }
    }
}
