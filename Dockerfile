# Etapa 1: Build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copia o arquivo pom.xml da pasta 'app' para o contêiner
COPY app/pom.xml ./app/pom.xml

# Copia o restante dos arquivos para o contêiner
COPY app/ /app/

# Baixar as dependências do Maven
RUN mvn -f /app/pom.xml clean install -DskipTests

# Etapa 2: Execução
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/wishlist-0.0.1-SNAPSHOT.jar /app/wishlist.jar
CMD ["java", "-jar", "/app/wishlist.jar"]
