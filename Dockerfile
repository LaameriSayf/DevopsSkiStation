# Use OpenJDK as the base image
FROM openjdk:17

<<<<<<< Updated upstream
EXPOSE 8089

ENV NEXUS_USER=admin
ENV NEXUS_PASSWORD=admin123

ADD http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar /gestion-station-ski.jar

HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:8089/actuator/health || exit 1

=======
# Expose the port the application will run on
EXPOSE 8089

# Do not include sensitive data like NEXUS_USER and NEXUS_PASSWORD directly in the Dockerfile

# Copy the JAR file into the container
COPY ./target/gestion-stationski-1.3.6-SNAPSHOT.jar /gestion-station-ski.jar

# Health check to ensure the application is up and running
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:8089/actuator/health || exit 1

# Run the application
>>>>>>> Stashed changes
ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
