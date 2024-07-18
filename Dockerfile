FROM ubuntu:latest as repo
RUN apt-get update \
    && apt-get install -y git 
RUN git clone https://github.com/Lynxtail/FirstMock.git

FROM maven:3.9.8-eclipse-temurin-17 as builder
WORKDIR /app
COPY --from=repo /FirstMock/.mvn/ .mvn
COPY --from=repo /FirstMock/mvnw /FirstMock/pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY --from=repo /FirstMock/src ./src
RUN ./mvnw clean install
RUN ls -la

FROM eclipse-temurin:17
COPY --from=builder /app/target/*.jar ./app.jar
COPY --from=repo /FirstMock/jolokia-agent-jvm-2.0.3-javaagent.jar ./jolokia.jar
EXPOSE 8080
RUN ["ls", "-la"]
ENTRYPOINT ["java", "-javaagent:/jolokia.jar", "-jar", "/app.jar"]