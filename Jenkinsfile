pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'devops-ski-station:latest'
        DOCKER_REGISTRY = 'docker.io'   
        DOCKER_USERNAME = credentials('docker-hub-username')
        DOCKER_PASSWORD = credentials('docker-hub-password')
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_13302c3b2c82ba9e780bffed41127a1f81466a9a'
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

        stage('SonarQube Analysis') {
            steps {
                script {
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

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_REGISTRY/$DOCKER_IMAGE .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASSWORD'
                    }
                    sh 'docker tag $DOCKER_REGISTRY/$DOCKER_IMAGE $DOCKER_USER/$DOCKER_IMAGE'
                    sh 'docker push $DOCKER_USER/$DOCKER_IMAGE'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying the Docker image"
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
