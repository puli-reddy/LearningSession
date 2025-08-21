FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/LearningSession.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]