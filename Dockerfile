FROM openjdk:11-jdk AS builder
WORKDIR /app
COPY . /app
RUN ./mvnw package -DskipTests


FROM openjdk:11-jre
ARG jar
ENV JAR $jar
COPY --from=builder /app/$JAR/target/*.jar /app/$JAR.jar
WORKDIR /app
ENTRYPOINT java -jar $JAR.jar


