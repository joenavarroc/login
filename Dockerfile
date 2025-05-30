# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Bash para wait-for-it
RUN apk add --no-cache bash

COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

# Copiar JAR desde build
COPY --from=build /app/target/agenda-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "mysql-agenda:3306", "--", "java", "-jar", "app.jar"]
