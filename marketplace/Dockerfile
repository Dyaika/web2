FROM openjdk:18-jdk-slim
WORKDIR /app
COPY build/libs/*all.jar app.jar
CMD ["java", "-jar", "app.jar"]