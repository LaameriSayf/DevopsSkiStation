pipeline {
    agent any

    environment {
        PROJECT_NAME = '4twin5-g5-gestion-stationski'
        IMAGE_NAME = 'abdulkareemmahmoud_g5_gestion-stationski'
        IMAGE_TAG = 'latest'
        NEXUS_REPO_URL = "http://192.168.33.10:8081/repository/gestionski/"
        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        SONARQUBE_URL = "http://192.168.33.10:9000"
        SONARQUBE_TOKEN = 'squ_1124f9454cb0bbaf5b31df5a8ca6ac146f6d68fe'
    }

    stages {
        stage('Clone') {
            steps {
                sh '''
                    echo "Cloning project..."
                    rm -rf DevopsSkiStation || true
                    git clone --branch AbdulkareemMahmoud_4TWIN5_G https://github.com/LaameriSayf/DevopsSkiStation.git
                '''
            }
        }

        stage('Set Version') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn versions:set -DnewVersion=1.3.6-SNAPSHOT'
                }
            }
        }

        stage('Compile') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('DevopsSkiStation') {
                    sh 'mvn test'
                }
            }
        }

        stage('SonarQube') {
            steps {
                dir('DevopsSkiStation') {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=${PROJECT_NAME} \
                          -Dsonar.host.url=${SONARQUBE_URL} \
                          -Dsonar.login=${SONARQUBE_TOKEN}
                    """
                }
            }
        }
    }
}
