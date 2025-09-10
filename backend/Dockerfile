# Java 21 Temel imajı
FROM eclipse-temurin:21-jdk-alpine

# Çalışma dizini
WORKDIR /app

# Jar dosyasını container'a kopyala
COPY target/*.jar app.jar

# Spring Boot uygulamasını çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
