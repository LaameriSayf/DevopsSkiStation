pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'M3', type: 'Maven'
        JAVA_HOME = tool name: 'JDK 17', type: 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your GitHub repository
                git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Build') {
            steps {
                // Compile the project using Maven
                script {
                    echo 'Building the project...'
                    sh "'${MAVEN_HOME}/bin/mvn' clean install"
                }
            }
        }

        stage('Test') {
            steps {
                // Run the unit tests using Maven
                script {
                    echo 'Running tests...'
                    sh "'${MAVEN_HOME}/bin/mvn' test"
                }
            }
        }

        stage('Package') {
            steps {
                // Package the project (e.g., creating a JAR, WAR file)
                script {
                    echo 'Packaging the project...'
                    sh "'${MAVEN_HOME}/bin/mvn' package"
                }
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the project (e.g., deploy to a server or repository)
                script {
                    echo 'Deploying the project...'
                    sh "'${MAVEN_HOME}/bin/mvn' deploy -DskipTests"
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }

        failure {
            echo 'Build or deployment failed. Please check the logs.'
        }

        always {
            cleanWs()  // Clean workspace after build
        }
    }
}
