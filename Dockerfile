# Etapa 1: Build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY app/pom.xml ./app/pom.xml

COPY app/ /app/

RUN mvn -f /app/pom.xml clean install -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/wishlist-0.0.1-SNAPSHOT.jar /app/wishlist.jar
CMD ["java", "-jar", "/app/wishlist.jar"]
