FROM ubuntu:latest

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . /app

RUN apt-get install maven -y
RUN cd app && mvn clean install -DskipTests

EXPOSE 8080

RUN cp -r /app/target/app-1.0.0.jar /app/app.jar

ENTRYPOINT [ "java", "-jar", "/app/app.jar"]