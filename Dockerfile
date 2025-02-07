FROM eclipse-temurin:17-jdk-alpine

ARG REDIRECT_URL \
    DB_URL \
    DB_USERNAME \
    DB_PASSWORD \
    COM_URL \
    CLS_URL \
    CLG \
    JOB_URL \
    SCH_URL \
    EVT_URL \
    ETC_URL \
    WEEKLY_URL

ENV SPRING_PROFILES_ACTIVE=prod \
    REDIRECT_URL=${REDIRECT_URL} \
    DB_URL=${DB_URL} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD} \
    COM_URL=${COM_URL} \
    CLS_URL=${CLS_URL} \
    CLG=${CLG_URL} \
    JOB_URL=${JOB_URL} \
    SCH_URL=${SCH_URL} \
    EVT_URL=${EVT_URL} \
    ETC_URL=${ETC_URL} \
    WEEKLY_URL=${WEEKLY_URL}

COPY ./build/libs/*SNAPSHOT.jar project.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "project.jar"]