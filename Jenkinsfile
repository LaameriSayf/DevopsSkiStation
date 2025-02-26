pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling the project...'
                script {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the project...'
                script {
                    sh 'mvn deploy -DskipTests'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment were successful!'
        }

        failure {
            echo 'Build or deployment failed. Please check the logs.'
        }

        always {
            cleanWs()
        }
    }
}
