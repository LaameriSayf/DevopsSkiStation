pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_cedfa64b26bbe8a2c0183fdf15eb5ca0a643816f'
        MAVEN_SETTINGS = 'C:/Program Files (x86)/Jenkins/.m2/settings.xml' // Correct path for Windows
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
                    // Use 'bat' for Windows, 'sh' for Linux/Mac
                    if (isUnix()) {
                        sh 'mvn clean compile'  // Linux/Mac
                    } else {
                        bat 'mvn clean compile'  // Windows
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'  // Linux/Mac
                    } else {
                        bat 'mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'  // Windows
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test -e -X'  // Linux/Mac
                    } else {
                        bat 'mvn test -e -X'  // Windows
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_PASSWORD')]) {
                    script {
                        def settingsXmlPath = 'C:/Program Files (x86)/Jenkins/.m2/settings.xml'

                        // Conditional shell command based on OS type
                        if (isUnix()) {
                            sh """
                                mvn deploy --settings ${settingsXmlPath} \
                                -DaltDeploymentRepository=github-repository::default::https://maven.pkg.github.com/LaameriSayf/DevopsSkiStation \
                                -Dusername=MahmoudAbdulkareem \
                                -Dpassword=${GITHUB_PASSWORD}
                            """
                        } else {
                            bat """
                                mvn deploy --settings ${settingsXmlPath} ^
                                -DaltDeploymentRepository=github-repository::default::https://maven.pkg.github.com/LaameriSayf/DevopsSkiStation ^
                                -Dusername=MahmoudAbdulkareem ^
                                -Dpassword=%GITHUB_PASSWORD%
                            """
                        }
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
