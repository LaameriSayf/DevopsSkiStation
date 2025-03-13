pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'  // Nom du serveur SonarQube configuré dans Jenkins
    }

    stages {
        // 1️⃣ Stage Git : Récupérer le code depuis Git
        stage('Git') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/Sayf']],  // Branche correcte
                        userRemoteConfigs: [[
                            url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                        ]]
                    ])
                }
            }
        }

        // 2️⃣ Stage Maven Build : Build du projet avec Maven
        stage('Maven Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        // 3️⃣ Stage Test : Lancer les tests Maven
        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        // 4️⃣ Stage SonarQube : Analyse du code avec SonarQube
        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=DevopsSkiStation \
                        -Dsonar.host.url=http://192.168.56.10:9000 \
                        -Dsonar.login=squ_0e9dee83242a96a31a6afc696aaecbbed0450196
                    '''
                }
            }
        }

        // 5️⃣ Stage Nexus Deployment : Déploiement sur Nexus Repository
     stage('Deploy to Nexus') {
         steps {
             sh 'mvn deploy -DrepositoryId=deploymentRepo -Dnexus.url=http://192.168.56.10:8081/repository/maven-releases/ -Dusername=admin -Dpassword=$NEXUS_API_KEY'
         }
     }

        stage('Build Docker Image') {
                    steps {
                        script {
                            def dockerImageName = 'sayflaameri/gestion-station-ski'
                            def dockerImageTag = 'latest'
                            sh "docker build -t ${dockerImageName}:${dockerImageTag} ."
                        }
                    }
                }

                stage('Push Docker Image') {
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                                sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                                sh "docker push sayflaameri/gestion-station-ski"
                            }
                        }
                    }
                }
        stage('Docker Compose') {
                    steps {
                        script {
                            sh 'docker-compose up -d'
                        }
                    }
                }
    }
}
