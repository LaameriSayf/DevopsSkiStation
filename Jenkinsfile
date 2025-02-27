pipeline {
    agent any

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
                    sh 'mvn test'
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
        always {
            script {
                echo "Pipeline execution finished successfully."
            }
        }
        failure {
            script {
                echo "Pipeline execution failed. Check logs for details."
            }
        }
    }
}
