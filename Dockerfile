FROM openjdk:14-alpine
COPY build/libs/customer-service-*-all.jar customer-service.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "customer-service.jar"]