FROM ubuntu:latest AS build

RUN apt update
RUN apt-get install software-properties-common -y
RUN apt-get install openjdk-22-jre-headless
RUN apt install openjdk-22-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:22-jdk-slim

EXPOSE 8000

COPY --from=build /target/news_co_api-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]