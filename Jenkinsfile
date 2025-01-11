pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *') 
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub') 
        IMAGE_NAME_SHELTERCAREAPP = 'username/sheltercareapp' 
        IMAGE_NAME_MYSQL = 'username/mysql' 
        IMAGE_NAME_PHPADMIN = 'username/phpmyadmin' 
        GIT_SSH_CREDENTIALS = credentials('GitHub_SSH') //Use Credentials for SSH Key
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:RaniaKedri1/devops_Project.git', credentialsId: 'GitHub_SSH'
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
        //Repeat similar structure for MySQL and PHPMyAdmin stages, adjusting the Dockerfile path as needed
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
        //Repeat Trivy scan for other images
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKERHUB_CREDENTIALS}") {
                        dockerImageSheltercareapp.push()
                        //push other images here
                    }
                }
            }
        }
    }
}
