FROM openjdk:22-jdk-slim AS build
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.yaml .
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=./application.yaml"]