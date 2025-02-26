pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: 'Sayf']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]],
                        extensions: [[$class: 'CloneOption', depth: 1, noTags: false, shallow: true]]  // Shallow Clone
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
        stage('Run Tests') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
    }
}
