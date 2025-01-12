pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // Polling SCM every 5 minutes
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')  // DockerHub credentials
        IMAGE_NAME_PREFIX = 'raniakedri22/'  // Your DockerHub username
        IMAGE_NAME_SHELTERCAREAPP = "${IMAGE_NAME_PREFIX}sheltercareapp"  // Image name for sheltercare app
        // Add more image names for other services if necessary
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/RaniaKedri1/devops_Project',
                    credentialsId: 'GitHub_SSH'  // GitHub SSH credentials
                script {
                    VERSION = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()  // Get short git hash as version
                }
            }
        }

        stage('Build Sheltercare App Image') {
            steps {
                script {
                    // Building Docker image for sheltercare app
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

        // Add similar stages for other images if required (like MySQL, PhpMyAdmin)
        // For example:
        // stage('Build Mysql Image') {
        //     steps {
        //         script {
        //             dockerImageMysql = docker.build("${IMAGE_NAME_PREFIX}mysql:${VERSION}", './mysql')
        //         }
        //     }
        // }
        // stage('Scan Mysql Image') {
        //     steps {
        //         script {
        //             sh "trivy image --severity MEDIUM ${IMAGE_NAME_PREFIX}mysql:${VERSION}"
        //         }
        //     }
        // }
        // stage('Push Mysql Image to Docker Hub') {
        //     steps {
        //         script {
        //             docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
        //                 dockerImageMysql.push()
        //             }
        //         }
        //     }
        // }
    }
}
