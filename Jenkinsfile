pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean compile -DskipTests'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    writeFile file: "$HOME/.m2/settings.xml", text: '''<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">

                        <servers>
                            <server>
                                <id>github-repository</id>
                                <username>MahmoudAbdulkareem</username> <!-- Your GitHub username -->
                                <password>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</password> <!-- Your GitHub token -->
                            </server>
                        </servers>

                    </settings>'''
                    sh 'mvn deploy -DskipTests -s $HOME/.m2/settings.xml'
                }
            }
        }

    }

    post {
        always {
            script {
                echo "Pipeline execution finished successfully."
            }
        }
        failure {
            script {
                echo "Pipeline execution failed. Check logs for details."
            }
        }
    }
}
