FROM gradle:7.4.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT java -jar -Dspring.profiles.active=production app.jar
