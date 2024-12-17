FROM openjdk:21
COPY .env /app/.env
COPY /target/vehicle-lookup-0.0.1-SNAPSHOT.jar /app/vehicle-lookup.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "vehicle-lookup.jar"]
