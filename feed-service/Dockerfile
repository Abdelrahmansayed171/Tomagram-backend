FROM ubuntu:latest
LABEL authors="Abdelrahman Sayed"

# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-oracle

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY target/feed-service-0.0.1-SNAPSHOT.jar /app/feed-service.jar

# Make port 8080 available to the world outside this container
EXPOSE 8085

# Run the JAR file
ENTRYPOINT ["java", "-jar", "feed-service.jar"]
