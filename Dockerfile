FROM ubuntu
RUN apt-get install git \
    && git clone https://github.com/Lynxtail/FirstMock.git

FROM eclipse-temurin:17 as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
COPY jolokia-agent-jvm-2.0.3-javaagent.jar ./
RUN ./mvnw clean install

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/target/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/app/jolokia-agent-jvm-2.0.3-javaagent.jar", "-jar", "/app/target/*.jar"]