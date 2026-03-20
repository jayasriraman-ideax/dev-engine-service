pipeline {
    agent any

    environment {
        IMAGE_NAME = "backend-app"
        CONTAINER_NAME = "backend-container"
        PORT = "8443"
    }

    stages {

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Stop & Remove Old Container') {
            steps {
                bat '''
                echo Stopping old container if exists...
                docker stop %CONTAINER_NAME% >nul 2>&1

                echo Removing old container if exists...
                docker rm %CONTAINER_NAME% >nul 2>&1

                exit 0
                '''
            }
        }

        stage('Run New Container') {
            steps {
                bat '''
                echo Starting new container...
                docker run -d -p %PORT%:%PORT% ^
                --name %CONTAINER_NAME% ^
                --restart unless-stopped ^
                %IMAGE_NAME%
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat '''
                echo Checking running containers...
                docker ps
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Backend deployed successfully!'
        }
        failure {
            echo '❌ Deployment failed!'
        }
        always {
            echo '📦 Pipeline execution completed.'
        }
    }
}