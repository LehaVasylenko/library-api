FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/consumer-telegram-1.jar consumertelegram.jar

EXPOSE 90

ENTRYPOINT ["java", "-jar", "consumertelegram.jar"]

HEALTHCHECK --interval=120s --timeout=10s --start-period=15s --retries=3 \
  CMD curl -f http://localhost:90/actuator/health || exit 1
