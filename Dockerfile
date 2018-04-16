FROM maven:3.5.3-jdk-8-alpine as builder
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:8-jre-slim
COPY --from=builder /app/initializr-service/target/initializr-service.jar /initializr-service.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /initializr-service.jar
