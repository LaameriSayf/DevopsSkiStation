pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        MAVEN_HOME = 'C:\\Maven'
        PATH = "${JAVA_HOME}\\bin;${MAVEN_HOME}\\bin;${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                script {
                    sh 'mvn clean install'
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

        stage('Package') {
            steps {
                echo 'Packaging the project...'
                script {
                    sh 'mvn package'
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
