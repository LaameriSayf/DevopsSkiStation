pipeline {
    agent any

    environment {
        GIT_SSL_NO_VERIFY = 'true' // Désactive la vérification SSL si nécessaire
    }

    stages {
        stage('Préparation') {
            steps {
                cleanWs()
                sh 'git config --global pack.threads 1' // Limite l'utilisation des threads
            }
        }

        stage('Checkout') {
            steps {
                retry(5) {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: 'origin/Sayf']], // Utilise la référence complète
                        extensions: [
                            [$class: 'CloneOption',
                             depth: 1,
                             noTags: true,
                             shallow: true], // Clone superficiel
                            [$class: 'LocalBranch',
                             localBranch: 'Sayf']
                        ],
                        userRemoteConfigs: [[
                            url: 'git@github.com:LaameriSayf/DevopsSkiStation.git',
                            credentialsId: 'jenkins-ssh-key',
                            timeout: 300 // Augmente le timeout à 5 minutes
                        ]]
                    ])
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests -T 1C' // Limite les threads Maven
            }
        }
    }

    post {
        failure {
            archiveArtifacts artifacts: '**/target/*.log', allowEmptyArchive: true
        }
    }
}