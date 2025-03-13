pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
    NEXUS_REPO = '192.168.33.10:5000'
    IMAGE_NAME = 'gestion-station-ski'
    IMAGE_TAG = 'latest'
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
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SQ1') {
                    sh 'mvn sonar:sonar'
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


                        stage('Docker Compose Up') {
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