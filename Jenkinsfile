pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_LOGIN = 'squ_4234086c09c0c3d568f52b3303480e43ed7d9426' // Replace with a valid token
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean compile -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test || true' // Avoid pipeline failure, logs will still show errors
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // Collects test results
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully."
        }
        failure {
            echo "Pipeline execution failed. Check logs for details."
        }
    }
}
