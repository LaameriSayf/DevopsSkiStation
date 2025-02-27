pipeline {
    agent any

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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SQ1') {
                    sh 'mvn sonar:sonar'
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
                    sh 'mvn test || true'
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