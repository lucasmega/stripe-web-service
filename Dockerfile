FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

RUN apt-get install maven -y
RUN mvn clean install

COPY . .

FROM openjdk:17-jdk-slim

EXPOSE 8080
COPY --from=build /target/StripeWebService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]