FROM ubuntu:22.04

RUN apt update && apt install -y maven

RUN apt install -y openjdk-17-jdk

COPY . /app

WORKDIR /app

RUN mvn package -DskipTests

CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]