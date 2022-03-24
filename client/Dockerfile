FROM openjdk:17
RUN #mkdir -p /var/opt/app
COPY ./target/*.jar /var/opt/app.jar
WORKDIR /var/opt/
ENTRYPOINT ["java", "-jar", "app.jar"]
