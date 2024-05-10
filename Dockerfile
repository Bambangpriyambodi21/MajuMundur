FROM openjdk:17-alpine
ADD tokopakedi-0.0.1-SNAPSHOT.jar tokopakedi-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/tokopakedi-0.0.1-SNAPSHOT.jar"]