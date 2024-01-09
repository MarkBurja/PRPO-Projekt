FROM eclipse-temurin:17-jre
CMD mkdir /app
WORKDIR /app
ADD ./api/target/api-1.0-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "api-1.0-SNAPSHOT.jar"]