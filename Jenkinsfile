pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://your-sonarqube-server:9000'  // Change this if needed
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build failed. Check logs for errors.'
        }
    }
}
