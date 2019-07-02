FROM openjdk:8-jre-alpine
# copy application WAR (with libraries inside)
COPY target/1-1.0-SNAPSHOT.jar /app.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=test", "/app.jar"]
