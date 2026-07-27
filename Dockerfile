FROM maven:3-eclipse-temurin-25-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -U -f pom.xml \
    && rm -f /app/target/original-*.jar # TODO add dep resolve and build stage separate cache layers

FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/OrangeJuice.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java -Xms256m -Xmx1g -jar OrangeJuice.jar"]