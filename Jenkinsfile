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
                        -Dsonar.login=sqa_5b84f2533f8e4f1c262920e14dc8e8b7644fcc14
                    '''
                }
            }
        }

     stage('Nexus') {
                steps {
                    script {
                        sh 'mvn deploy'
                    }
                }
            }

   // 6️⃣ Construction de l'image Docker
        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageName = 'sayflaameri/gestion-station-ski'
                    def dockerImageTag = 'latest'

                    sh 'echo "📁 Contenu du workspace actuel :" && pwd && ls -R'

                    sh "ls -l target/gestion-station-ski-1.0.jar || exit 1"

                    // Corrige ici selon l'emplacement que tu trouves :
                    sh "docker build -t ${dockerImageName}:${dockerImageTag} -f Dockerfile ."
                }
            }
        }




          // 7️⃣ Push vers DockerHub
     stage('Push Docker Image') {
         steps {
             script {
                 withCredentials([usernamePassword(
                     credentialsId: 'docker-hub-credentials',
                     usernameVariable: 'DOCKERHUB_USERNAME',
                     passwordVariable: 'DOCKERHUB_PASSWORD'
                 )]) {
                     sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                     sh "docker push sayflaameri/gestion-station-ski:latest"
                 }
             }
         }
     }


          // 8️⃣ Déploiement avec Docker Compose
         stage('Docker Compose') {
             steps {
                 script {
                     sh 'ls -l && cat docker-compose.yml' // debug, optionnel
                     sh 'docker-compose down || true'
                     sh 'docker-compose up -d'
                 }
             }
         }
          stage('Grafana') {
                        steps {
                            script {
                                def grafanaUrl = 'http://192.168.56.10:3000/d/haryan-jenkins/jenkins3a-performance-and-health-overview'
                                withCredentials([usernamePassword(credentialsId: 'credential_grafana', usernameVariable: 'GRAFANA_USERNAME', passwordVariable: 'GRAFANA_PASSWORD')]) {
                                    def curlCommand = "curl -X GET -u ${GRAFANA_USERNAME}:${GRAFANA_PASSWORD} -H 'Content-Type: application/json' ${grafanaUrl}"
                                    sh curlCommand
                                }
                            }
                         }
                         }
                        stage('Mailing Test') {
                                   steps {
                                       script {
                                           emailext(
                                               to: 'eya.bouthouri@esprit.tn',
                                               subject: "✅ Test email Jenkins",
                                               body: """Hello,

                       Ceci est un test d'envoi de mail depuis Jenkins. 🎉

                       Build: ${env.JOB_NAME} #${env.BUILD_NUMBER}
                       Lien: ${env.BUILD_URL}
                       """,
                                               mimeType: 'text/plain'
                                           )
                                       }
                                   }
                               }

      }
  }

