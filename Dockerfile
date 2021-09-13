FROM openjdk:8u292-jre

VOLUME /tmp
ADD target/camunda-ticket-service.jar /opt/camunda-ticket-service/camunda-ticket-service.jar
WORKDIR /opt/camunda-ticket-service
ENTRYPOINT ["java","-jar","camunda-ticket-service.jar"]