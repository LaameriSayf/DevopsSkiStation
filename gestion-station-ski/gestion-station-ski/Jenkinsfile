pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/Sayf']],  // Correction ici
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }
         stage(' maven build ') {
                    steps{
                        script {
                            sh 'mvn clean install'
                        }
                    }
                }
    }
}
