# Use the OpenJDK 17 image as a base
FROM openjdk:17-jdk-slim

LABEL authors="Kami"

WORKDIR /app

# Copy the jar file from the target directory to the container
COPY target/ashenbound-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]