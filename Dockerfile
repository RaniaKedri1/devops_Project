# Use a lightweight base image for Java
FROM openjdk:23-jdk-slim

# Set a working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/shelterCareApp.jar app.jar

# Expose the application's port
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
