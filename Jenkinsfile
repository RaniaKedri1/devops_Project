pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *') // Poll GitHub repository every 5 minutes
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub') // Your DockerHub credentials ID
        IMAGE_NAME_SHELTERCAREAPP = 'username/sheltercareapp' // Replace 'username' with your Docker Hub username
        IMAGE_NAME_MYSQL = 'username/mysql' // Replace 'username' with your Docker Hub username
        IMAGE_NAME_PHPADMIN = 'username/phpmyadmin' // Replace 'username' with your Docker Hub username
        GIT_SSH_COMMAND = 'ssh -i /var/jenkins_home/.ssh/id_rsa -o StrictHostKeyChecking=no' // Ensure the right SSH key is used
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'git@github.com:RaniaKedri1/devops_Project.git',
                    credentialsId: 'GitHub_SSH' // Ensure this matches the SSH credential ID
            }
        }
        stage('Build ShelterCareApp Image') {
            steps {
                dir('sheltercareapp') {
                    script {
                        dockerImageSheltercareapp = docker.build("${IMAGE_NAME_SHELTERCAREAPP}")
                    }
                }
            }
        }
        stage('Build MySQL Image') {
            steps {
                dir('mysql') { // Ensure the MySQL Dockerfile is in the 'mysql' directory
                    script {
                        dockerImageMysql = docker.build("${IMAGE_NAME_MYSQL}")
                    }
                }
            }
        }
        stage('Build PHPMyAdmin Image') {
            steps {
                dir('phpmyadmin') { // Ensure the phpMyAdmin Dockerfile is in the 'phpmyadmin' directory
                    script {
                        dockerImagePhpmyadmin = docker.build("${IMAGE_NAME_PHPADMIN}")
                    }
                }
            }
        }
        stage('Scan ShelterCareApp Image') {
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                    aquasec/trivy:latest image --exit-code 0 \
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
                    aquasec/trivy:latest image --exit-code 0 \
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
                    aquasec/trivy:latest image --exit-code 0 \
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
                        dockerImageMysql.push()
                        dockerImagePhpmyadmin.push()
                    }
                }
            }
        }
    }
}
