FROM openjdk:21-jdk
COPY ./target/core-0.0.1-SNAPSHOT.jar app.jar

ARG SPOTIFY_CLIENT_ID
ARG SPOTIFY_CLIENT_SECRET

ENV SPOTIFY_CLIENT_ID $SPOTIFY_CLIENT_ID
ENV SPOTIFY_CLIENT_SECRET $SPOTIFY_CLIENT_SECRET

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
