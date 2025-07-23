pipeline {
  agent any

  parameters {
    choice(name: 'PIPELINE_ACTION', choices: ['build', 'deploy'], description: 'Selecciona la acción: build o deploy')
    string(name: 'ENVIRONMENT', defaultValue: 'dev', description: 'Nombre del entorno: dev, prod, etc.')
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
      when {
        expression { params.PIPELINE_ACTION == 'build' }
      }
      agent {
        docker {
          image 'maven:3.8.5-eclipse-temurin-17'
          args '-v /var/run/docker.sock:/var/run/docker.sock' // para que el contenedor tenga acceso a Docker
        }
      }
      steps {
        sh 'mvn clean package -DskipTests'
        sh 'docker build -t $IMAGE_NAME .'
        withDockerRegistry([ credentialsId: 'dockerhub-credentials-id', url: '' ]) {
          sh 'docker push $IMAGE_NAME'
        }
      }
    }

    stage('Clone Ops Repo') {
      when {
        expression { params.PIPELINE_ACTION == 'deploy' }
      }
      steps {
        dir('personas-service-ops') {
          git credentialsId: 'github-credentials-id', url: 'https://github.com/lerambot/personas-service-ops.git', branch: 'master'
        }
      }
    }

    stage('Deploy to Kubernetes') {
      when {
        expression { params.PIPELINE_ACTION == 'deploy' }
      }
      steps {
        sh "kubectl apply -f personas-service-ops/${params.ENVIRONMENT}/"
      }
    }
  }

  post {
    success {
      echo "✅ Acción '${params.PIPELINE_ACTION}' ejecutada correctamente en entorno '${params.ENVIRONMENT}'."
    }
    failure {
      echo "❌ Falló la acción '${params.PIPELINE_ACTION}' en entorno '${params.ENVIRONMENT}'."
    }
  }
}
