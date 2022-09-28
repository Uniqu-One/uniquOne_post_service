FROM openjdk:11
COPY build/libs/*.jar uniquone_post_img.jar
ENTRYPOINT ["java", "-jar", "uniquone_post_img.jar"]