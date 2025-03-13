# Use OpenJDK as the base image
FROM openjdk:17

# Expose application port
EXPOSE 8089

# Download the JAR file from Nexus and add it to the container (with credentials)
ADD http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar /gestion-station-ski.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
