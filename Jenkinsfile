pipeline {
    agent any

  tools {
        sonar 'SonarQube Server'  
    }

    stages {
        stage('Checkout') {
            steps {
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

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                script {
                    withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=squ_377a618e5e81fe20dbf2c375969455a0fefb5eeb -Dsonar.host.url=http://192.168.33.10:9000'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                script {
                    sh 'mvn test -e -X'
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
            echo 'Build or deployment failed. Please check the logs for more details.'
        }

        always {
            cleanWs()
        }
    }
}
