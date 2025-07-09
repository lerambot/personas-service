pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        DOCKERHUB_USERNAME = 'lerambot' // 🔴 tu usuario Docker Hub
        IMAGE_NAME = 'lerambot/personas-service' // 🔴 cambia si tu repo de Docker tiene otro nombre
    }

    stages {
        stage('Checkout') {
    		steps {
        		git credentialsId: 'github-credentials-id', url: 'https://github.com/lerambot/personas-service.git', branch: 'develop'
    		}
		}

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withDockerRegistry([ credentialsId: 'dockerhub-credentials-id', url: '' ]) {
                    sh 'docker push $IMAGE_NAME'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline finalizado con éxito.'
        }
        failure {
            echo 'El pipeline falló.'
        }
    }
}
