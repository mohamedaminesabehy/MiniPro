FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/Projet-1.0.0.jar Projet-1.0.0.jar

CMD ["java", "-jar", "/Projet-1.0.0.jar"]