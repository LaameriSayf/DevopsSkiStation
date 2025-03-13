# Utiliser une image de base OpenJDK 17
FROM openjdk:17-jdk-slim

# Exposer le port 8090 (correspond à server.port dans application.properties)
EXPOSE 8090

# Créer un répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR dans le conteneur
ADD target/ouday_oueslati-0.1.8.jar oudayserv.jar

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "oudayserv.jar"]