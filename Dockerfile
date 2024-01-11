FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM adoptopenjdk/openjdk11:latest
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
