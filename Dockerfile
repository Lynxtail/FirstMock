FROM maven:3.9.8-eclipse-temurin-17-focal as builder
RUN apt-get update \
    && apt-get install -y git 
RUN git clone https://github.com/Lynxtail/FirstMock.git
WORKDIR /FirstMock
RUN chmod +x mvnw 
RUN ./mvnw clean package

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /FirstMock/target/*.jar /app/app.jar
COPY --from=builder /FirstMock/jolokia-*.jar /app/jolokia.jar
EXPOSE 8080 8778
ENTRYPOINT ["java", "-javaagent:/app/jolokia.jar=port=8778,host=0.0.0.0", "-jar", "/app/app.jar"]