FROM eclipse-temurin:17.0.3_7-jre-alpine
ADD eureka-server/build/libs/eureka-server-0.0.1-SNAPSHOT.jar /opt/root.jar
ENTRYPOINT ["java", "-jar", "/opt/root.jar"]