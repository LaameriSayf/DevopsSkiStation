pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: 'Sayf']],  // Corrected branch name
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }
        stage('Maven Build') {
            steps {
                script {
                    sh 'mvn clean install'
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
}
