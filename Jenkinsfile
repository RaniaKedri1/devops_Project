pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')  // Polling SCM every 5 minutes
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')  // ID of the secret text (PAT) credential
        DOCKER_USERNAME = 'raniakedri22'  // Your Docker Hub username
        IMAGE_NAME_PREFIX = "${DOCKER_USERNAME}/devops_project"  // Image name for Docker Hub
    }

    tools {
        maven 'Maven 3.9.9'  // Specify Maven 3.9.9
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'git@github.com:RaniaKedri1/devops_Project.git',
                    credentialsId: 'GitHub_SSH'  // GitHub credential ID for SSH access to repo
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image using Dockerfile in the project directory
                    def dockerfilePath = './DockershelterCare/Dockerfile'  // Specify path to Dockerfile
                    if (fileExists(dockerfilePath)) {
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_PREFIX}:${VERSION}", dockerfilePath)
                    } else {
                        error "Dockerfile not found at ${dockerfilePath}"
                    }
                }
            }
        }

        stage('Scan Docker Image') {
            steps {
                script {
                    // Vulnerability scan with Trivy
                    def scanResult = sh(script: """
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                        aquasec/trivy:latest image --exit-code 1 \\
                        --severity LOW,MEDIUM,HIGH,CRITICAL \\
                        --timeout 60m \\
                        ${IMAGE_NAME_PREFIX}:${VERSION}
                    """, returnStatus: true)

                    if (scanResult != 0) {
                        error "Vulnerability scan failed with exit code ${scanResult}"
                    } else {
                        echo "Vulnerability scan completed successfully."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Push image to Docker Hub
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        echo "Pushing image ${IMAGE_NAME_PREFIX}:${VERSION} to Docker Hub..."
                        dockerImageSheltercareapp.push()
                    }
                }
            }
        }
    }
}
