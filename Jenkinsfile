pipeline {
    agent any

    environment {
<<<<<<< Updated upstream
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64/"
        M2_HOME = "/opt/apache-maven-3.6.3"
        PATH = "$M2_HOME/bin:$PATH"
        SONAR_HOST_URL = 'http://192.168.33.10:9000'
        SONAR_LOGIN = 'squ_4234086c09c0c3d568f52b3303480e43ed7d9426'
        NEXUS_REPO = '192.168.33.10:5000'
        IMAGE_NAME = 'gestion-station-ski'
        IMAGE_TAG = 'latest'
        NEXUS_USER = 'admin'
        NEXUS_PASSWORD = '12345678'
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'Mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Compile Stage') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test Stage') {
            steps {
                bat 'mvn -X test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
             withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                                bat 'mvn sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${SONAR_TOKEN}'
                            }
        }

        stage('Nexus Deploy') {
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                    bat "mvn deploy -DskipTests -Dgithub.token=${GITHUB_TOKEN}"
=======
        IMAGE_NAME = 'mahmoudabdulkareem1/gestion-stationski'
        IMAGE_TAG = 'latest'

        NEXUS_PROTOCOL = 'http'
        NEXUS_HOST = '192.168.33.10'
        NEXUS_PORT = '8081'  // Changed to 8081 for Nexus repository
        NEXUS_REPO_URL = "${NEXUS_PROTOCOL}://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/"
        NEXUS_REPO = 'gestionski'  // Make sure this matches the repository name in Nexus

        NEXUS_CREDENTIAL_ID = 'NEXUS_CREDENTIAL'
        DOCKERHUB_CREDENTIALS = credentials('Docker_ID')
    }

    stages {
        stage('Clone Repository') {
            steps {
                sh 'rm -rf DevOpCheck'
                sh 'git clone --branch Mahmoud https://github.com/MahmoudAbdulkareem/DevOpCheck.git'
            }
        }

        stage('Update Version in POM') {
            steps {
                dir('DevOpCheck') {
                    sh 'mvn versions:set -DnewVersion=1.3.6-SNAPSHOT'
>>>>>>> Stashed changes
                }
            }
        }

<<<<<<< Updated upstream
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${NEXUS_REPO}/${IMAGE_NAME}:${IMAGE_TAG} ."
=======
        stage('Compile') {
            steps {
                dir('DevOpCheck') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir('DevOpCheck') {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDENTIAL_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    dir('DevOpCheck') {
                        sh "mvn deploy -DaltDeploymentRepository=nexus::default::${NEXUS_REPO_URL} -s .jenkins/settings.xml -e"
                    }
                }
            }
        }

        stage('Docker IMAGE') {
            steps {
                script {
                    def startTime = System.currentTimeMillis()
                    try {
                        sh '''
                        echo "Building Docker image..."
                        docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                        '''
                    } finally {
                        def endTime = System.currentTimeMillis()
                        def duration = (endTime - startTime) / 1000
                        echo "Docker image build duration: ${duration}s"
                    }
>>>>>>> Stashed changes
                }
            }
        }

<<<<<<< Updated upstream
        stage('Push to Nexus') {
            steps {
                script {
                    sh "docker login -u ${NEXUS_USER} -p ${NEXUS_PASSWORD} ${NEXUS_REPO}"
                    sh "docker push ${NEXUS_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
=======
        stage('Docker HUB') {
            steps {
                script {
                    def startTime = System.currentTimeMillis()
                    try {
                        sh '''
                        echo "Logging into Docker Hub..."
                        echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                        echo "Pushing image to Docker Hub..."
                        docker push ${IMAGE_NAME}:${IMAGE_TAG}
                        '''
                    } finally {
                        def endTime = System.currentTimeMillis()
                        def duration = (endTime - startTime) / 1000
                        echo "Docker Hub push duration: ${duration}s"
                    }
>>>>>>> Stashed changes
                }
            }
        }

<<<<<<< Updated upstream
        stage('Docker Compose Up') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d'
=======
        stage('DOCKER-COMPOSE') {
            steps {
                script {
                    def startTime = System.currentTimeMillis()
                    try {
                        sh '''
                        set -e
                        echo "Checking for docker-compose.yml..."
                        ls -la
                        if [ ! -f docker-compose.yml ]; then
                            echo "Error: docker-compose.yml not found!"
                            exit 1
                        fi
                        echo "Checking Docker image..."
                        docker images | grep ${IMAGE_NAME}
                        echo "Stopping and removing mysql-test container if it exists..."
                        if docker ps -a --format '{{.Names}}' | grep -q "^mysql-test$"; then
                            echo "Stopping and removing mysql-test container..."
                            docker stop mysql-test || true
                            docker rm mysql-test || true
                        fi
                        echo "Checking ports in use..."
                        docker ps -a --format '{{.Names}} {{.Ports}}'
                        echo "Exporting IMAGE_TAG for Docker Compose..."
                        export IMAGE_TAG=${IMAGE_TAG}
                        echo "Starting Docker Compose with IMAGE_TAG=${IMAGE_TAG}..."
                        docker compose up -d --build
                        echo "Checking running containers..."
                        docker compose ps
                        '''
                    } finally {
                        def endTime = System.currentTimeMillis()
                        def duration = (endTime - startTime) / 1000
                        echo "Docker Compose duration: ${duration}s"
                    }
                }
>>>>>>> Stashed changes
            }
        }
    }

    post {
        success {
<<<<<<< Updated upstream
            echo "Pipeline executed successfully."
        }
        failure {
            echo "Pipeline execution failed. Check logs for details."
=======
            echo "✅ Deployment Successful!"
        }
        failure {
            echo "❌ Deployment Failed! Check logs."
>>>>>>> Stashed changes
        }
    }
}
