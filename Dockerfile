FROM openjdk:17-jdk-slim

EXPOSE 8090

WORKDIR /app

ADD target/ouday_oueslati-0.1.8.jar oudayserv.jar

ENTRYPOINT ["java", "-jar", "oudayserv.jar"]