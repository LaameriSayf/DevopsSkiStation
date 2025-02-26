pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_TOKEN = 'squ_cedfa64b26bbe8a2c0183fdf15eb5ca0a643816f'
        MAVEN_HOME = '/opt/maven'  // Adjust this to your Maven installation path on the Jenkins server
        MAVEN_REPO = '/var/jenkins_home/.m2/repository'  // Adjust to Jenkins Maven repo path
        MAVEN_SETTINGS = '/var/jenkins_home/.m2/settings.xml'  // Path to settings.xml on Jenkins
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
                        // Adjust the path to settings.xml
                        def settingsXmlPath = '/var/jenkins_home/.m2/settings.xml'

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
