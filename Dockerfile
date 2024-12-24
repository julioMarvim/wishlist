# Etapa 1: Build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copia o pom.xml e o diretório src/
COPY app/pom.xml ./pom.xml
COPY app/src/ ./src/

RUN mvn clean install -DskipTests

# Etapa 2: Execução
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/wishlist-0.0.1-SNAPSHOT.jar /app/wishlist.jar
CMD ["java", "-jar", "/app/wishlist.jar"]
