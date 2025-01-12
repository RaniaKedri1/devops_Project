pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // Consider webhooks for better efficiency
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')  // DockerHub credentials
        IMAGE_NAME_PREFIX = 'raniakedri22/'  // DockerHub image prefix
    }

    stages {
        stage('Checkout') {
            steps {
                try {
                    // Checkout from GitHub repository
                    git branch: 'main', url: 'https://github.com/RaniaKedri1/devops_Project', credentialsId: 'GitHub_SSH'
                    script {
                        // Get the short Git commit hash as the version tag
                        VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    }
                } catch (Exception e) {
                    // Handle Git checkout failure
                    error("Git checkout failed: ${e.message}")
                    currentBuild.result = 'FAILURE'
                    return // Stop the pipeline immediately if git checkout fails
                }
            }
        }

        stage('Build Images') {
            steps {
                script {
                    try {
                        // Build the Docker image for Sheltercare app
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}", './DockershelterCare', '-f ./DockershelterCare/Dockerfile')
                        // Add other Docker builds here if needed
                    } catch (Exception e) {
                        // Handle Docker build failure
                        error("Docker build failed: ${e.message}")
                        currentBuild.result = 'FAILURE'
                        return
                    }
                }
            }
        }

        stage('Scan Images') {
            steps {
                script {
                    try {
                        // Run Trivy scan for vulnerabilities
                        sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}sheltercareapp:${VERSION}"
                        // Add other Trivy scan commands here if needed
                    } catch (Exception e) {
                        // Handle Trivy scan failure
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
                    try {
                        // Push the Docker image to DockerHub
                        docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                            dockerImageSheltercareapp.push()
                            // Add other Docker push commands here if needed
                        }
                    } catch (Exception e) {
                        // Handle Docker push failure
                        error("Docker push failed: ${e.message}")
                        currentBuild.result = 'FAILURE'
                        return
                    }
                }
            }
        }
   
 }

}
