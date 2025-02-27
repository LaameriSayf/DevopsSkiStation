# Utilisez une image de base de Java
FROM maven:3.8.4-openjdk-11

# Copiez votre projet dans l'image
WORKDIR /app
COPY . .

# Construisez le projet avec Maven
RUN mvn clean install

# Exposez le port de l'application (si nécessaire)
EXPOSE 8080

# Commande à exécuter lors du démarrage du conteneur
CMD ["java", "-jar", "target/gestion-station-ski-1.0.jar"]
