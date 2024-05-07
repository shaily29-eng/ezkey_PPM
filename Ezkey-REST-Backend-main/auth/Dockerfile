FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR deps
COPY build/libs/auth-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR app
COPY --from=builder deps/dependencies/ ./
COPY --from=builder deps/spring-boot-loader/ ./
COPY --from=builder deps/snapshot-dependencies/ ./
COPY --from=builder deps/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]