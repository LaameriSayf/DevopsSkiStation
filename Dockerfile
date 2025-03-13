# Use a lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy only the necessary files to avoid unnecessary layers
COPY target/gestionstationski-1.0.5.jar app.jar
#COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8089

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]