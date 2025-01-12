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
        IMAGE_NAME_PREFIX = 'raniakedri22/'
        IMAGE_NAME_SHELTERCAREAPP = "${IMAGE_NAME_PREFIX}sheltercareapp"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/RaniaKedri1/devops_Project',
                    credentialsId: 'GitHub_SSH'
                script {
                    VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                }
            }
        }

        stage('Build Sheltercare App') {
            steps {
                script {
                    // Build the application and create JAR file
                    sh 'mvn clean package'  // Ensure Maven is set up correctly in Jenkins
                }
            }
        }

        stage('Build Sheltercare App Image') {
            steps {
                script {
                    // Build Docker image using the context of the current directory
                    dockerImageSheltercareapp = docker.build("${IMAGE_NAME_SHELTERCAREAPP}")
                }
            }
        }

        stage('Scan Sheltercare App Image') {
            steps {
                script {
                    // Run Trivy scan for vulnerabilities
                    sh """
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                        aquasec/trivy:latest image --exit-code 1 \\
                        --severity LOW,MEDIUM,HIGH,CRITICAL \\
                        --timeout 60m \\
                        ${IMAGE_NAME_SHELTERCAREAPP}:${VERSION}
                    """
                }
            }
        }

        stage('Push Sheltercare App Image to Docker Hub') {
            steps {
                script {
                    // Push Sheltercare app image to Docker Hub
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        dockerImageSheltercareapp.push()
                    }
                }
            }
        }
    }
}
