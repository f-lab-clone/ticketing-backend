FROM openjdk:17-jdk-slim as builder
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle gradle
COPY src src
RUN chmod +x ./gradlew

ARG SPRING_PROFILES_ACTIVE

RUN ./gradlew bootjar -Pspring.profiles.active=$SPRING_PROFILES_ACTIVE
RUN java -Djarmode=layertools -jar build/libs/*.jar extract

FROM openjdk:17-jdk-slim as runtime
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]