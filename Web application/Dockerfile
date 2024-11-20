FROM bellsoft/liberica-openjdk-alpine-musl:21
COPY ./mitigia-task/target/mitigia-task-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]