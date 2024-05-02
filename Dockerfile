FROM ubuntu:latest AS build

RUN apt update
RUN apt install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8000

COPY --from=build /target/news_co_api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]