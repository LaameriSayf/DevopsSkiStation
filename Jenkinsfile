pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mahmoudabdulkareem/gestion-stationski'
        IMAGE_TAG = 'latest'

        NEXUS_PROTOCOL = 'http'
        NEXUS_HOST = '192.168.33.10'
        NEXUS_PORT = '8081'
        NEXUS_REPO = 'gestionski'
        NEXUS_REPO_URL = "${NEXUS_PROTOCOL}://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/"

        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        DOCKERHUB_CREDENTIALS = credentials('Docker_ID')
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning repository...'
                    sh '''
                        if [ -d "DevopsSkiStation" ]; then
                            echo "Removing existing DevopsSkiStation directory..."
                            rm -rf DevopsSkiStation
                        fi
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

        stage('Docker IMAGE') {
            steps {
                script {
                    def startTime = System.currentTimeMillis()
                    echo 'Building Docker image...'
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
                    def endTime = System.currentTimeMillis()
                    echo "Docker image build duration: ${(endTime - startTime) / 1000}s"
                }
            }
        }

        stage('Docker HUB') {
            steps {
                script {
                    def startTime = System.currentTimeMillis()
                    echo 'Logging into Docker Hub...'
                    sh '''
                    echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                    echo "Pushing image to Docker Hub..."
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    '''
                    def endTime = System.currentTimeMillis()
                    echo "Docker Hub push duration: ${(endTime - startTime) / 1000}s"
                }
            }
        }

         stage('Deploy with Docker Compose') {
            steps {
                echo '🚀 Deploying with Docker Compose...'
                        sh 'docker compose up -d'
                    }
                }
            }

    post {
        success {
            echo "✅ Deployzment Successful!"
        }
        failure {
            echo "❌ Deployment Failed! Check logs."
        }
    }
}