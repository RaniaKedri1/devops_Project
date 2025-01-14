# ShelterCare

ShelterCare is a Spring Boot application designed for shelter management.

## Prerequisites

- Docker
- Kubernetes
- Helm
- Java 17 (for building the application)

## Setup Instructions

1. Clone the repository:
git clone https://github.com/your-repository/sheltercare.git

arduino
Copier le code

2. Build the ShelterCare app Docker image:
docker build -t raniakedri22/sheltercareapp:latest .

arduino
Copier le code

3. Deploy the app using Helm:
helm install sheltercare ./charts/sheltercare

markdown
Copier le code

4. Access the ShelterCare app through your Kubernetes cluster.

## Services

- MySQL: MySQL database for ShelterCare.
- phpMyAdmin: UI for MySQL management.

## License

MIT License