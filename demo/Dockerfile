FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/library-0.0.1.jar library.jar

ENTRYPOINT ["java", "-jar", "library.jar"]

HEALTHCHECK --interval=120s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
