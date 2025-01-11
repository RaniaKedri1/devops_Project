pipeline {
    agent any
    triggers {
        pollSCM('H /5 * * * *')  // Polls GitHub repository every 5 minutes
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')  // Your DockerHub credentials ID
        IMAGE_NAME_SHELTERCAREAPP = 'username/sheltercareapp'  // Replace 'username' with your Docker Hub username
        IMAGE_NAME_MYSQL = 'username/mysql'  // Replace 'username' with your Docker Hub username
        IMAGE_NAME_PHPADMIN = 'username/phpmyadmin'  // Replace 'username' with your Docker Hub username
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:RaniaKedri1/devops_Project.git', credentialsId: 'GitHub_SSH'
            }
        }
        stage('Build ShelterCareApp Image') {
            steps {
                script {
                    // Build the Docker image for your ShelterCareApp container
                    dockerImageSheltercareapp = docker.build("${IMAGE_NAME_SHELTERCAREAPP}")
                }
            }
        }
        stage('Build MySQL Image') {
            steps {
                script {
                    // Build the Docker image for MySQL container
                    dockerImageMysql = docker.build("${IMAGE_NAME_MYSQL}", 'mysql')
                }
            }
        }
        stage('Build PHPMyAdmin Image') {
            steps {
                script {
                    // Build the Docker image for phpMyAdmin container
                    dockerImagePhpmyadmin = docker.build("${IMAGE_NAME_PHPADMIN}", 'phpmyadmin')
                }
            }
        }
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKERHUB_CREDENTIALS}") {
                        // Push images to Docker Hub
                        dockerImageSheltercareapp.push()
                        dockerImageMysql.push()
                        dockerImagePhpmyadmin.push()
                    }
                }
            }
        }
    }
}
