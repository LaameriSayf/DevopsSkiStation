pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_cedfa64b26bbe8a2c0183fdf15eb5ca0a643816f'
        MAVEN_HOME = 'C:/Program Files/Maven'  // Path to Maven installation
        MAVEN_REPO = 'C:/Users/Negam/.m2/repository'  // Local repository
        MAVEN_SETTINGS = 'C:/Users/Negam/.m2/settings.xml'  // Path to settings.xml
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
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
                    sh 'mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test -e -X'
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_PASSWORD')]) {
                    script {
                        def settingsXmlPath = 'C:/Users/Negam/.m2/settings.xml'  // Path to settings.xml

                        sh """
                            mvn deploy --settings ${settingsXmlPath} \
                            -DaltDeploymentRepository=github-repository::default::https://maven.pkg.github.com/LaameriSayf/DevopsSkiStation \
                            -Dusername=MahmoudAbdulkareem \
                            -Dpassword=$GITHUB_PASSWORD
                        """
                    }
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
