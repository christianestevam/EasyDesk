FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte para o container
COPY src ./src

# Compila o código e gera o arquivo .jar
RUN mvn clean package -DskipTests

# Verifica se o .jar foi gerado corretamente
RUN ls -la target/

# Fase final para criar a imagem que executará a aplicação
FROM openjdk:21-jdk
WORKDIR /app

# Copia o .jar gerado na fase de build para a fase final
COPY --from=build /app/target/easydesk-0.0.1-SNAPSHOT.jar /app/app.jar

# Comando para rodar a aplicação Spring Boot
CMD ["java", "-jar", "/app/app.jar"]
