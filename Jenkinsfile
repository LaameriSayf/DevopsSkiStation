pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
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
                script {
                    sh "mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://${NEXUS_REPO}/repository/maven-releases/"
                }
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