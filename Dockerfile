FROM gradle:8.6.0-jdk17

MAINTAINER "Jules BOBEUF"

WORKDIR /app

COPY . .

EXPOSE 8080