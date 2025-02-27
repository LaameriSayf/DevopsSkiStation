pipeline {
    agent any

 environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_cedfa64b26bbe8a2c0183fdf15eb5ca0a643816f'  // Replace this with the actual token in a secure way
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
                    sh 'mvn clean compile'
                }
            }
        }

       stage('SonarQube Analysis') {
            steps {
                script {
                    // Use mvn to trigger SonarQube analysis
                    sh "mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}"
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
