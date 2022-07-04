FROM openjdk:11
COPY target/spring-boot-vaccination.jar spring-boot-vaccination.jar
ENTRYPOINT ["java","-jar","spring-boot-vaccination.jar"]
