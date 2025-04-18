pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
    IMAGE_NAME = 'eyachamekh-g5-stationski'
    IMAGE_TAG = 'latest'
    DOCKER_HUB_CREDS = credentials('dockerhub-credentials')
    DOCKER_USERNAME = 'eyachamekh'
    NEXUS_HOST = '192.168.33.10'
    NEXUS_PORT = '8081'
    NEXUS_PROTOCOL = 'http'
    NEXUS_REPO_NAME = 'maven-releases' // or maven-snapshots if you're pushing a snapshot
    NEXUS_REPO_URL = "${NEXUS_PROTOCOL}://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO_NAME}/"
    NEXUS_CREDENTIAL_ID = 'nexus'
     }

    stages {
        stage('Git') {
            steps {
                script {
                    git branch: 'eya', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

        stage('MAVEN Build') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test || true'
                }
            }
        }

        stage('SonarQube'){
            steps {
                withSonarQubeEnv('SQ1') {
                    sh 'mvn sonar:sonar'
                }
            }

        }
            stage('Nexus') {
                steps {
                    withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        echo 'Deploying to Nexus...'
                        sh "mvn deploy -DaltDeploymentRepository=nexus::default::${NEXUS_REPO_URL} -s .jenkins/settings.xml -e"
                    }
                }
            }


        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker Image..."
                    sh 'docker build -t ${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} .'
                }
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker Image to Docker Hub..."
                    sh 'docker push eyachamekh/eyachamekh-g5-stationski:latest'
                }
            }
        }




        stage('Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
        }

             stage('Mailing Test') {
                        steps {
                            echo " Envoi de mail de test réussi."
                            mail to: 'chamekheya1@gmail.com',
                                 subject: 'Test de Jenkins mail',
                                 body: 'This is a plain Jenkins email using the basic "mail" step.'
                        }
                    }
                }

                post {
                    success {
                        echo ' Pipeline exécuté avec succès.'
                        mail to: 'chamekheya1@gmail.com',
                             subject: 'Succès du Pipeline - gestionski',
                             body: """
            Bonjour Eya,

            Bravo, le pipeline Jenkins s’est exécuté avec succès. 🎉✅

            ✔ Projet : gestionski
            📅 Date : ${new Date()}

            Cordialement,
            Jenkins
            """
                    }

                    failure {
                        echo '❌ Le pipeline a échoué.'
                        mail to: 'chamekheya1@gmail.com',
                             subject: 'Échec du Pipeline - gestionski',
                             body: """
            Bonjour Eya,

            Le pipeline Jenkins a échoué. 🚨

            ✔ Projet : gestionski
            📅 Date : ${new Date()}

            Merci de consulter Jenkins pour plus de détails.

            Cordialement,
            Jenkins
            """
                    }
                }



    post {
        success {
                echo "Pipeline execution finished successfully."
        }
        failure {
                echo "Pipeline execution failed. Check logs for details."
        }
    }
}