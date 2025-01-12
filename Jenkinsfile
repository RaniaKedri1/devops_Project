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
                git branch: 'main', url: 'https://github.com/RaniaKedri1/devops_Project', credentialsId: 'GitHub_SSH'
                script {
                    VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                }
            }
        }
        stage('Build Images') {
            steps {
                script {
                    try {
                        //Corrected line:
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}", './DockershelterCare', '-f ./DockershelterCare/Dockerfile')
                        // ... (rest of your build commands, still commented out) ...
                    } catch (Exception e) {
                        error("Image build failed: ${e.message}")
                        currentBuild.result = 'FAILURE'
                        return
                    }
                }
            }
        }
        stage('Scan Images') {
            steps {
                script {
                    try{
                        sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}"
                        // ... (rest of your scan commands, still commented out) ...
                    } catch (Exception e) {
                        error("Trivy scan failed: ${e.message}")
                        currentBuild.result = 'FAILURE'
                        return
                    }

                }
            }
        }
        stage('Push Images') {
            steps {
                script {
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        dockerImageSheltercareapp.push()
                        // ... (rest of your push commands, still commented out) ...
                    }
                }
            }
        }
    }
}
