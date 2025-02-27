pipeline {
    agent any

    environment {
        DB_NAME = 'test_db'
        DB_USER = 'root'
        DB_PASS = ''
        DB_PORT = '3306'
        MYSQL_CONTAINER = 'mysql-test'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: 'mahmoud', url: 'https://github.com/LaameriSayf/DevopsSkiStation.git'
                }
            }
        }

      
        stage('Start MySQL') {
            steps {
                script {
                    sh '''
                    set -e
                    echo "Starting MySQL..."

                    if docker ps -a --format '{{.Names}}' | grep -q "^$MYSQL_CONTAINER$"; then
                        if docker ps --format '{{.Names}}' | grep -q "^$MYSQL_CONTAINER$"; then
                            echo "MySQL container is already running."
                        else
                            echo "MySQL container exists but is stopped. Restarting..."
                            docker start $MYSQL_CONTAINER
                        fi
                    else
                        echo "Starting a new MySQL container..."
                        docker run --name $MYSQL_CONTAINER \
                            -e MYSQL_DATABASE=$DB_NAME \
                            -e MYSQL_ROOT_PASSWORD=$DB_PASS \
                            -p $DB_PORT:3306 \
                            -d mysql:8
                    fi

                    echo "Waiting for MySQL to be ready (10 sec)..."
                    sleep 10

                    if ! docker ps --format '{{.Names}}' | grep -q "^$MYSQL_CONTAINER$"; then
                        echo "MySQL did not start correctly!"
                        exit 1
                    fi

                    echo "Checking MySQL container logs..."
                    docker logs $MYSQL_CONTAINER | tail -n 20
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SQ1') {
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
    }

    post {
        always {
            script {
                echo "Pipeline execution finished."
            }
        }
        failure {
            script {
                echo "An error occurred in the pipeline."
            }
        }
    }
}
