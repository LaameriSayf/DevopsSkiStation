pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
    IMAGE_NAME = 'eyachamekh-g5-stationski'
    IMAGE_TAG = 'latest'
    GITHUB = credentials('github-creds')
    NEXUS_REPO_URL = "${NEXUS_PROTOCOL}://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/"
    NEXUS_CREDENTIAL_ID = 'nexus'
     }

    stages {
        stage('Git') {
            steps {
                script {
                    git branch: 'eya', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

        stage('MAVEN Build') {
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test || true'
                }
            }
        }

        stage('SonarQube'){
            steps {
                withSonarQubeEnv('SQ1') {
                    sh 'mvn sonar:sonar'
                }
            }

        }





            stage('Build Docker Image') {
                steps {
                    script {
                        echo "Building Docker Image..."
                        sh "sudo docker build -t eyachamekh-g5-stationski:latest ."
                    }
                }
            }

        stage('Push to Docker Hub') {
            steps {
                script {
                    echo "Tagging Docker image..."
                    sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} eyachamekh/${IMAGE_NAME}:${IMAGE_TAG}"

                    echo "Pushing Docker image to Docker Hub..."
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        docker.image("eyachamekh/${IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }



        stage('Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
        }


    }

    post {
        success {
                echo "Pipeline execution finished successfully."
        }
        failure {
                echo "Pipeline execution failed. Check logs for details."
        }
    }
}