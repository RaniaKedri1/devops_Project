pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // Polling SCM every 5 minutes
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')  // Docker Hub credentials
        IMAGE_NAME_SHELTERCAREAPP = 'username/sheltercareapp'  // ShelterCareApp image
        IMAGE_NAME_MYSQL = 'username/mysql'  // MySQL image
        IMAGE_NAME_PHPADMIN = 'username/phpmyadmin'  // PHPMyAdmin image
        GIT_SSH_CREDENTIALS = credentials('GitHub_SSH')  // SSH Key for GitHub access
    }

    tools {
        maven 'Maven  3.9.9' // Use Maven tool labeled as "Maven 3.x" in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/RaniaKedri1/NewDevopsProject.git',
                    credentialsId: 'GitHub_SSH' // GitHub SSH credentials
            }
        }

        stage('Build ShelterCareApp Image') {
            steps {
                dir('sheltercareapp') {
                    script {
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_SHELTERCAREAPP}", '-f Dockerfile .')
                    }
                }
            }
        }

        stage('Build MySQL Image') {
            steps {
                dir('mysql') {
                    script {
                        dockerImageMySQL = docker.build("${IMAGE_NAME_MYSQL}", '-f Dockerfile .')
                    }
                }
            }
        }

        stage('Build PHPMyAdmin Image') {
            steps {
                dir('phpmyadmin') {
                    script {
                        dockerImagePHPAdmin = docker.build("${IMAGE_NAME_PHPADMIN}", '-f Dockerfile .')
                    }
                }
            }
        }

        stage('Scan ShelterCareApp Image') {
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                    aquasec/trivy:latest image \
                    --severity LOW,MEDIUM,HIGH,CRITICAL \
                    ${IMAGE_NAME_SHELTERCAREAPP}
                    """
                }
            }
        }

        stage('Scan MySQL Image') {
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                    aquasec/trivy:latest image \
                    --severity LOW,MEDIUM,HIGH,CRITICAL \
                    ${IMAGE_NAME_MYSQL}
                    """
                }
            }
        }

        stage('Scan PHPMyAdmin Image') {
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                    aquasec/trivy:latest image \
                    --severity LOW,MEDIUM,HIGH,CRITICAL \
                    ${IMAGE_NAME_PHPADMIN}
                    """
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKERHUB_CREDENTIALS}") {
                        dockerImageSheltercareapp.push()
                        dockerImageMySQL.push()
                        dockerImagePHPAdmin.push()
                    }
                }
            }
        }
    }
}
