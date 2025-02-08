FROM eclipse-temurin:17-jdk-alpine

ENV SPRING_PROFILES_ACTIVE=prod

COPY ./build/libs/*SNAPSHOT.jar project.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "project.jar"]