pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-credentials', branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
    }
}
