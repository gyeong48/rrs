FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/rrs-0.0.1-SNAPSHOT.jar /app/rrs.jar
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]