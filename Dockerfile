#Build stage
FROM maven:3.9.9-amazoncorretto-17-al2023 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy AS run

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=optional:file:/run/src/main/resources/application.properties"]
