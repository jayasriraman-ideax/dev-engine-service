pipeline {
    agent any

    environment {
        IMAGE_NAME = "backend-app"
        CONTAINER_NAME = "backend-container"
        PORT = "8443"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/jayasriraman-ideax/dev-engine-service.git'
            }
        }

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

        stage('Stop Old Container') {
            steps {
                bat '''
                docker stop %CONTAINER_NAME% 2>nul
                docker rm %CONTAINER_NAME% 2>nul
                '''
            }
        }

        stage('Run Container') {
            steps {
                bat '''
                docker run -d -p %PORT%:%PORT% ^
                --name %CONTAINER_NAME% ^
                %IMAGE_NAME%
                '''
            }
        }

        stage('Verify Container') {
            steps {
                bat 'docker ps'
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
    }
}