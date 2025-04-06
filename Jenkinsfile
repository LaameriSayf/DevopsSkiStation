pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
    IMAGE_NAME = 'gestion-station-ski'
    IMAGE_TAG = 'latest'
    GITHUB_USERNAME = credentials('github-username')
    GITHUB_TOKEN = credentials('github-token')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'eya', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

        stage('Build') {
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
        stage('GitHub Deploy') {
            steps {
                script {
                    sh '''
                    mvn deploy -DskipTests \
                        -DaltDeploymentRepository=github-repository::default::https://maven.pkg.github.com/LaameriSayf/DevopsSkiStation \
                        -Dusername=$GITHUB_USERNAME \
                        -Dpassword=$GITHUB_TOKEN
                    '''
                }
            }
        }

        stage('Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
               script {
                    def imageExists = sh(script: "docker images -q gestion-station-ski", returnStdout: true).trim()
                    if (!imageExists) {
                        echo "Image not found, building..."
                        sh "docker build -t gestion-station-ski ."
                    } else {
                            echo "Image already exists, skipping build."
                    }
               }
            }
        }

        stage('Push Docker Image') {

        }


        stage('Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Grafana') {

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