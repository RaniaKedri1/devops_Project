# 🌟 ShelterCare Project 🌟

Welcome to the **ShelterCare** project—a comprehensive solution for managing shelter operations. This application leverages modern technologies to ensure scalability, security, and efficient deployment.

## 🚀 Project Overview

**ShelterCare** is designed to streamline shelter management through the following key components:

- **Spring Boot**: The core framework for building the application.
- **Docker**: Containerization of the application for consistent environments.
- **Kubernetes (K8s)**: Orchestration of containers for deployment and scaling.
- **Jenkins**: Automation of Continuous Integration and Continuous Deployment (CI/CD) pipelines.
- **Prometheus & Helm**: Monitoring and management of application performance.

## 🛠️ Technologies Used

- **Spring Boot**: Framework for building Java-based applications.
- **Docker**: Platform for developing, shipping, and running applications in containers.
- **Kubernetes**: System for automating deployment, scaling, and management of containerized applications.
- **Jenkins**: Open-source automation server for building, deploying, and automating projects.
- **Prometheus**: Open-source system monitoring and alerting toolkit.
- **Helm**: Package manager for Kubernetes, facilitating deployment and management of applications.

## 📜 Setup and Configuration

### 1. Jenkins CI/CD Pipeline

The **Jenkins pipeline** automates the following processes:

1. **Source Code Checkout**: Pulls the latest code from the `main` branch of the GitHub repository.
2. **JAR File Verification**: Ensures that the `shelterCareApp.jar` file exists in the `target` directory.
3. **Docker Image Build**: Containerizes the application by building a Docker image.
4. **Vulnerability Scan**: Scans the Docker image using **Trivy** to detect security vulnerabilities.
5. **Docker Image Push**: Pushes the Docker image to **Docker Hub** under the repository `raniakedri22/sheltercareapp`.

The entire pipeline is managed through the `Jenkinsfile`, automating all steps from code checkout to image pushing. Jenkins can be configured to trigger this pipeline on code changes or on a scheduled basis.

### 2. Docker 🐳

The application is containerized using **Docker**. Here's how Docker is utilized:

#### **Dockerfile**:

The `Dockerfile` is configured to:

- Copy the `shelterCareApp.jar` file from the `target` directory into the container.
- Use the **OpenJDK** base image to run the Spring Boot application.
- Expose port `8080` for the application.

#### **Docker Image Build**:

The Jenkins pipeline builds the Docker image using the following command:

```bash
docker build -t raniakedri22/sheltercareapp .
Docker Image Push:
Once the Docker image is built, it's pushed to Docker Hub with the tag raniakedri22/sheltercareapp:{commit hash}.

3. Kubernetes (K8s) ☸️
Kubernetes is used for orchestrating the containerized application. The application is deployed to a local Kubernetes cluster using Docker Desktop's Kubernetes feature or a cloud Kubernetes service like AWS EKS, GKE, or Azure AKS.

Kubernetes Deployment:
Deploy the application to Kubernetes using a deployment.yaml manifest or a Helm chart.

Scaling & High Availability:
Kubernetes ensures that the application is highly available by managing the scaling and load balancing of the containers across multiple nodes in the cluster.

4. Monitoring with Prometheus & Helm 📊
Monitoring is implemented using Prometheus and managed with Helm:

Prometheus: Collects and stores metrics from the application and infrastructure.
Helm: Simplifies the deployment and management of Prometheus on Kubernetes.
🔄 Workflow
Code Changes: Developers push their changes to the GitHub repository.
Jenkins Pipeline: Jenkins detects changes and triggers the CI/CD pipeline.
Build & Test: The pipeline builds the application, ensures the JAR exists, and scans for vulnerabilities.
Docker Image Build: A Docker image is built and pushed to Docker Hub.
Deployment: The Docker image is deployed to Kubernetes for staging or production use.
🏃‍♂️ How to Run
1. Docker 🐳
To run the application using Docker:

Build the Docker image:

bash
Copier le code
docker build -t raniakedri22/sheltercareapp .
Run the Docker container:

bash
Copier le code
docker run -d -p 8080:8080 raniakedri22/sheltercareapp
Access the application: Visit http://localhost:8080 to view the application.

2. Kubernetes ☸️
To deploy the application on Kubernetes:

Apply the Kubernetes manifest:

bash
Copier le code
   kubectl apply -f deployment.yaml
   Check the status of the pods:

bash
Copier le code
   kubectl get pods
   Access the application:

Forward the service port:

bash
Copier le code
kubectl port-forward service/sheltercareapp 8080:8080
Visit: http://localhost:8080 to access the application.

## 🖥️ Monitoring with Kubernetes & Prometheus

To monitor the status of the Kubernetes pods running the application and related services, use the following `kubectl` command:

```bash
kubectl get pods -n monitoring

![alt text](monitoring.png)