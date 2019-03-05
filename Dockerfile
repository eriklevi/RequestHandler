FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/RequestHandler-0.0.1-SNAPSHOT.jar request-handler.jar
ENTRYPOINT ["java", "-jar", "/request-handler.jar"]
EXPOSE 8762
