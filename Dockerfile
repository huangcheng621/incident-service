FROM docker.io/openjdk:17
ADD ./target/insident-service-1.0-SNAPSHOT.jar incident-service.jar
EXPOSE 8080
ENTRYPOINT  java   -XX:InitialRAMPercentage=70.0  -XX:MaxRAMPercentage=70.0 -XX:MinRAMPercentage=70.0 incident-service.jar
