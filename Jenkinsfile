  stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-credentials', branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Name must match Jenkins SonarQube config
                    sh 'mvn sonar:sonar -Dsonar.projectKey=your_project_key -Dsonar.host.url=http://your-sonarqube-server:9000 -Dsonar.login=your_sonar_token'
                }
            }
        }