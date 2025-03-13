FROM openjdk:11
EXPOSE 8089

# Copier le JAR généré par Maven dans l'image Docker
COPY target/gestion-station-ski-1.0.jar gestion-station-ski-1.0.jar

# Correction du nom du fichier JAR dans l'ENTRYPOINT
ENTRYPOINT ["java", "-jar", "gestion-station-ski-1.0.jar"]
