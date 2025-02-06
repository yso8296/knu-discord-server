FROM eclipse-temurin:17-jdk-alpine

ENV SPRING_PROFILES_ACTIVE=prod \
    DB_URL=${DB_URL} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD}

COPY ./build/libs/*SNAPSHOT.jar project.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "project.jar"]