pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_cedfa64b26bbe8a2c0183fdf15eb5ca0a643816f'
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
                    // Use mvn to clean and compile
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
                    // Use mvn to run tests
                    sh 'mvn test -e -X'
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_PASSWORD')]) {
                    script {
                        def settingsXmlPath = 'C:/Program Files (x86)/Jenkins/.m2/settings.xml'

                        // Use mvn to deploy with specified settings
                        sh """
                            mvn deploy --settings ${settingsXmlPath} ^
                            -DaltDeploymentRepository=github-repository::default::https://maven.pkg.github.com/LaameriSayf/DevopsSkiStation ^
                            -Dusername=MahmoudAbdulkareem ^
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
