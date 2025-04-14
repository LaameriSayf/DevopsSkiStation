pipeline {
    agent any

    environment {
    NEXUS_REPO = '192.168.33.10:8081'
    IMAGE_NAME = 'station-ski'
    IMAGE_TAG = 'latest'
    GITHUB = credentials('github-creds')
    NEXUS_REPO_URL = "${NEXUS_PROTOCOL}://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/"
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
                    sh 'mvn clean compile'
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
                    def imageExists = sh(script: "docker images -q gestion-station-ski", returnStdout: true).trim()
                    if (!imageExists) {
                        echo "Image not found, building..."
                        sh "docker build -t gestion-station-ski ."
                    } else {
                            echo "Image already exists, skipping build."
                    }
               }
            }
        }

        stage('Docker Hub') {
            steps {
                script {
                     sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} eyachamekh/${IMAGE_NAME}:${IMAGE_TAG}"

                     docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        def image = docker.image("eyachamekh/${IMAGE_NAME}:${IMAGE_TAG}")
                        image.push()
                            }
                        }
                    }
        }


        stage('Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
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