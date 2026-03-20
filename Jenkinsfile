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
                git 'YOUR_BACKEND_REPO_URL'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                docker stop $CONTAINER_NAME || true
                docker rm $CONTAINER_NAME || true
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker run -d -p $PORT:$PORT \
                --name $CONTAINER_NAME \
                $IMAGE_NAME
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
    }
}