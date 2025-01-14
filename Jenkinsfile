pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // Polling SCM every 5 minutes
    }

    tools {
        maven 'Maven 3.x' // Use the name you provided in Global Tool Configuration
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME_PREFIX = 'raniakedri22/'  // Your DockerHub username
        IMAGE_NAME_SHELTERCAREAPP = "${IMAGE_NAME_PREFIX}sheltercareapp"  // Image name for sheltercare app
        JAR_PATH = 'target/shelterCareApp.jar' // Path to the JAR file
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/RaniaKedri1/devops_Project',
                    credentialsId: 'GitHub_SSH'
                script {
                    // Get the short commit hash for versioning
                    VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                }
            }
        }

        stage('Verify JAR File Exists') {
            steps {
                script {
                    // Verify if the JAR file exists in the target directory
                    def jarExists = fileExists("${WORKSPACE}/target/shelterCareApp.jar")
                    if (!jarExists) {
                        error "shelterCareApp.jar not found in target directory."
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Verifying the existence of Dockerfile and building Docker image
                    def dockerfilePath = 'Dockerfile'
                    if (fileExists(dockerfilePath)) {
                        // Use the pre-built JAR file in the Docker build context
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_SHELTERCAREAPP}:${VERSION}", "-f ${dockerfilePath} .")
                    } else {
                        error "Dockerfile not found in the project root directory"
                    }
                }
            }
        }

        stage('Scan Docker Image') {
            steps {
                script {
                    // Scan the Docker image with Trivy
                    def scanResult = sh(script: """
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                        aquasec/trivy:latest image --exit-code 1 \\
                        --severity LOW,MEDIUM,HIGH,CRITICAL \\
                        --timeout 60m \\
                        ${IMAGE_NAME_SHELTERCAREAPP}:${VERSION}
                    """, returnStatus: true)

                    if (scanResult != 0) {
                        error "Vulnerability scan failed with exit code ${scanResult}"
                    } else {
                        echo "Vulnerability scan completed successfully."
                    }
                }
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                script {
                    // Using Docker Hub credentials to authenticate and push the image
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        echo "Pushing image ${IMAGE_NAME_SHELTERCAREAPP}:${VERSION} to Docker Hub..."
                        dockerImageSheltercareapp.push() // Pushing the image with version tag
                    }
                }
            }
        }
    }
}
