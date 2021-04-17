
# ------------------------------------------------------------------------------
# Build Stage
# ------------------------------------------------------------------------------

FROM openjdk:8-jdk-alpine as java-builder

WORKDIR /usr/src/

COPY . .

RUN ./gradlew bootJar

# ------------------------------------------------------------------------------
# Final Stage
# ------------------------------------------------------------------------------

FROM openjdk:8-jdk-alpine

COPY --from=java-builder /usr/src/build/libs/login-jwt-angular-0.0.1-SNAPSHOT.jar app.jar

ENV POSTGRES_DATABASE_CONN=jdbc:postgresql://127.0.0.1:5432/db_evaluacion_login

EXPOSE 8080

CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar app.jar
