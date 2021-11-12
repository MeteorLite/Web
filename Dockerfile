FROM openjdk:11
COPY build/libs/Web-0.0.1-SNAPSHOT.jar web.jar
ENTRYPOINT ["java", "-jar", "/web.jar"]
EXPOSE 8080