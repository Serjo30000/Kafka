FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY --chmod=0755 mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY src src
COPY build-elements/run.sh run.sh

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine

ARG WORKSPACE=/workspace/app
ARG DEPENDENCY=/workspace/app/target/dependency
ARG APPUSER=ant
ARG APPGROUP=ant
ENV APPLICATIONCLASS=com.apiService.apiMovieReviews.ApiMovieReviewsApplication

RUN addgroup -S ${APPGROUP} && adduser -S ${APPUSER} -G ${APPGROUP}
USER ${APPUSER}

COPY --from=build --chmod=0755 ${WORKSPACE}/run.sh /run.sh
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app


ENTRYPOINT ["/run.sh"]