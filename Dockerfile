# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090:9090
CMD ["java", "-jar", "app.jar"]
