FROM openjdk:latest
LABEL maintainer="smc"
COPY ./docker-*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]