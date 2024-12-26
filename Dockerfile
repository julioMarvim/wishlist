FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY app/pom.xml ./pom.xml
COPY app/src/ ./src/

RUN mvn clean install -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/wishlistEntity-0.0.1-SNAPSHOT.jar /app/wishlistEntity.jar
CMD ["java", "-jar", "/app/wishlistEntity.jar"]
