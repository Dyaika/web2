# Используем официальный образ Gradle для сборки приложения
FROM gradle:8.3-jdk17 AS builder

# Копируем исходный код в контейнер
WORKDIR /web2
COPY ./build.gradle ./build.gradle
COPY ./marketplace-settings/marketplace-settings.gradle ./settings.gradle
COPY ./common/ ./common
COPY ./marketplace ./marketplace

# Очищаем кеш Gradle и собираем приложение
RUN gradle clean build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /web2/marketplace/build/libs/marketplace-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]