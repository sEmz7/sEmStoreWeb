FROM openjdk:21-oracle
WORKDIR /app
COPY target/*.jar app/app.jar
COPY .env .env
EXPOSE 8089
ENTRYPOINT ["java","-jar","app/app.jar"]