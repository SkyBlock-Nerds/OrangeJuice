FROM maven:3-eclipse-temurin-25-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -U -f pom.xml \
    && rm -f /app/target/original-*.jar

FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/OrangeJuice.jar
ENTRYPOINT ["sh", "-c", "exec java -Xms256m -Xmx1g ${JAVA_OPTS} -jar OrangeJuice.jar"]