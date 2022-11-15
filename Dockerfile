FROM adoptopenjdk/openjdk11
COPY build/libs/msa_post_service-0.0.1-SNAPSHOT.jar app/post-service.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "post-service.jar"]