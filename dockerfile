FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/Projet-0.0.1-SNAPSHOT.jar Projet-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/Projet-0.0.1-SNAPSHOT.jar"]