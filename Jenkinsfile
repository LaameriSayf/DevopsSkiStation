pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                script {
                    checkout([$class: 'GitSCM', branches: [[name: 'Sayf']], userRemoteConfigs: [[url: 'https://github.com/LaameriSayf/DevopsSkiStation.git']]])
                }
            }
        }

        }