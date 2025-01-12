pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // Consider using webhooks if on GitHub
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME_PREFIX = 'raniakedri22/' // Use prefix for your DockerHub repo
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', 
                    url: 'git@github.com:RaniaKedri1/devops_Project.git', 
                    credentialsId: 'GitHub_SSH'
                script {
                    VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                }
            }
        }

        stage('Build Images') {
            steps {
                script {
                    dockerImageSheltercareapp = docker.build("${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}", './DockershelterCare')
                    // Uncomment below lines if required
                    // dockerImageMysql = docker.build("${IMAGE_NAME_PREFIX}mysql:${VERSION}", './mysql')
                    // dockerImagePhpmyadmin = docker.build("${IMAGE_NAME_PREFIX}phpmyadmin:${VERSION}", './phpmyadmin')
                }
            }
        }

        stage('Scan Images') {
            steps {
                script {
                    sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}"
                    // Uncomment below lines if required
                    // sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}mysql:${VERSION}"
                    // sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}phpmyadmin:${VERSION}"
                }
            }
        }

        stage('Push Images') {
            steps {
                script {
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        dockerImageSheltercareapp.push()
                        // Uncomment below lines if required
                        // dockerImageMysql.push()
                        // dockerImagePhpmyadmin.push()
                    }
                }
            }
        }
    }
}
