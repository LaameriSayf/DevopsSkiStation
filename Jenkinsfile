pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mahmoudabdulkareem/gestion-stationski'
        IMAGE_TAG = 'latest'
        NEXUS_REPO_URL = "http://192.168.33.10:8081/repository/gestionski/"
        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        DOCKERHUB_CREDENTIALS = credentials('Docker_ID')
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning repository...'
                    sh '''
                        rm -rf DevopsSkiStation || true
                        git clone --branch AbdulkareemMahmoud_4TWIN5_G https://github.com/LaameriSayf/DevopsSkiStation.git
                    '''
                }
            }
        }

        stage('Update Version in POM') {
            steps {
                echo 'Updating version in pom.xml...'
                sh 'mvn versions:set -DnewVersion=1.3.6-SNAPSHOT'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling project...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    echo 'Deploying to Nexus...'
                    sh "mvn deploy -DaltDeploymentRepository=nexus::default::${NEXUS_REPO_URL} -s .jenkins/settings.xml -e"
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    echo 'Building and pushing Docker image...'
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo '🚀 Deploying with Docker Compose...'
                sh 'docker compose up -d'
            }
        }

        stage('Mailing Test') {
            steps {
                echo "✅ Envoi de mail de test réussi."
            }
        }
    }

    post {
        always {
            echo "🧹 Nettoyage Docker"
            sh 'docker-compose down'
        }

        success {
            echo '✅ Pipeline exécuté avec succès.'
            emailext(
                subject: "✅ Succès du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins s’est exécuté avec succès. 🎉

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}
                    📊 Rapport SonarQube joint en PDF

                    Cordialement,
                    Jenkins
                """,
                to: 'negamex4274@gmail.com',
                attachmentsPattern: 'sonar-report.pdf'
            )
        }

        failure {
            echo '❌ Le pipeline a échoué.'
            emailext(
                subject: "❌ Échec du Pipeline - DevopsSkiStation",
                body: """
                    Bonjour,

                    Le pipeline Jenkins a échoué. 🚨

                    ✔ Projet : DevopsSkiStation
                    📅 Date : ${new Date()}

                    Merci de consulter Jenkins pour plus de détails.

                    Cordialement,
                    Jenkins
                """,
                to: 'negamex4274@gmail.com'
            )
        }
    }
}
