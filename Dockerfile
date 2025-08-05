FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/LearningSession-null.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]