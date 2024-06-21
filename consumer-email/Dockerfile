FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/consumer-email-1.jar consumermail.jar

EXPOSE 91

ENTRYPOINT ["java", "-jar", "consumermail.jar"]

HEALTHCHECK --interval=120s --timeout=10s --start-period=15s --retries=3 \
  CMD curl -f http://localhost:91/actuator/health || exit 1
