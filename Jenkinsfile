pipeline {
  agent any

  parameters {
    string(name: 'ENVIRONMENT', defaultValue: 'dev', description: 'Nombre del entorno: dev, prod, pre, etc.')
  }

  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
    DOCKERHUB_USERNAME = 'lerambot'
    IMAGE_NAME = 'lerambot/personas-service'
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

    stage('Clone Ops Repo') {
      steps {
        dir('personas-service-ops') {
          git credentialsId: 'github-credentials-id', url: 'https://github.com/lerambot/personas-service-ops.git', branch: 'develop'
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        sh "kubectl apply -f personas-service-ops/${params.ENVIRONMENT}/"
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline finalizado con éxito.'
    }
    failure {
      echo '❌ El pipeline falló.'
    }
  }
}
