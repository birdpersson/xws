FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/mail_service
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/mail_service/target/mail_service.jar ./
EXPOSE 8080
CMD java -jar mail_service.jar
