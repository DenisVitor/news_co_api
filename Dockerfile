FROM ubuntu:latest AS build

RUN apt update && apt install -y software-properties-common \
    && add-apt-repository ppa:pushkarnk/jdk22 \
    && apt update \
    && apt install -y default-jre \
    && apt install -y openjdk-22-jdk \
    && apt install -y maven \
    && apt clean

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:22-jdk-slim

EXPOSE 8000

COPY --from=build /target/news_co_api-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
