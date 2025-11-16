FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src
COPY .mvn .mvn

RUN mvn clean install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine

ARG DEPENDENCY=/workspace/app/target/dependency
ARG APPUSER=ant
ARG APPGROUP=ant
ENV APPLICATIONCLASS=com.dataService.movieReviews.MovieReviewsApplication

RUN addgroup -S ${APPGROUP} && adduser -S ${APPUSER} -G ${APPGROUP}

USER ${APPUSER}

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.dataService.movieReviews.MovieReviewsApplication"]
