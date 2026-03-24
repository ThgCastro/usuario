FROM gradle:9.4.1-alpine as build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM eclipse-temurin:25

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/usuario.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/usuario.jar"]