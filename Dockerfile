FROM gradle:8.5.0-jdk17

LABEL MAINTAINER="Jules BOBEUF"

WORKDIR /app

COPY . .

EXPOSE 8080

CMD ["gradle", "bootRun", "-Penv=dev"]