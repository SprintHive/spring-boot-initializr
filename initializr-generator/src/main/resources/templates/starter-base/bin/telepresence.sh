#!/bin/sh

./gradlew clean bootJar

telepresence --swap-deployment {{name}}-v1 \
  --docker-run --rm \
  -v$(pwd)/build/libs:/app \
  -p 8080:8080 \
  openjdk:8-jre-slim \
  sh -c "java -jar /app/{{name}}-0.0.1-SNAPSHOT.jar"
